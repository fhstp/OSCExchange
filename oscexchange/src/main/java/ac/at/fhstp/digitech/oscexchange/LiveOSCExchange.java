package ac.at.fhstp.digitech.oscexchange;

import com.illposed.osc.MessageSelector;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCMessageListener;
import com.illposed.osc.OSCSerializeException;
import com.illposed.osc.messageselector.OSCPatternAddressMessageSelector;
import com.illposed.osc.transport.udp.OSCPortIn;
import com.illposed.osc.transport.udp.OSCPortOut;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Optional;

import ac.at.fhstp.digitech.oscexchange.errors.OSCError;
import ac.at.fhstp.digitech.oscexchange.errors.OSCMessageError;
import ac.at.fhstp.digitech.oscexchange.errors.OSCParsingError;
import ac.at.fhstp.digitech.oscexchange.errors.OSCPortClosingError;
import ac.at.fhstp.digitech.oscexchange.errors.OSCPortOpeningError;
import ac.at.fhstp.digitech.oscexchange.errors.OSCPortUseError;
import ac.at.fhstp.digitech.oscexchange.errors.OSCValidationError;

class LiveOSCExchange {

    static void start(OSCExchange exchange, OSCDevicePair devicePair) {
        Optional<OSCPortOut> outPort = tryGenerateOutPort(devicePair.remote);
        Optional<OSCPortIn> inPort = tryGenerateInPort(devicePair.local);

        if (!outPort.isPresent())
            fail(exchange, new OSCPortOpeningError(OSCPort.Out));
        else if (!inPort.isPresent())
            fail(exchange, new OSCPortOpeningError(OSCPort.In));
        else
            run(new LiveOSCExchange(exchange, outPort.get(), inPort.get()));
    }

    private static void fail(OSCExchange exchange, OSCError error) {
        if (exchange.errorListener != null)
            exchange.errorListener.handle(error);
    }

    private static void complete(OSCExchange exchange) {
        exchange.completeListener.handle();
    }

    private static Optional<OSCPortOut> tryGenerateOutPort(InetSocketAddress address) {
        try {
            return Optional.of(new OSCPortOut(address));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private static Optional<OSCPortIn> tryGenerateInPort(InetSocketAddress address) {
        try {
            OSCPortIn port = new OSCPortIn(address);
            return Optional.of(port);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private static void run(LiveOSCExchange live) {
        handleNextRequest(live);
    }

    private static boolean hasRequest(LiveOSCExchange live) {
        return live.requestIndex < OSCExchange.getRequestCount(live.exchange);
    }

    private static void complete(LiveOSCExchange live) {
        try {
            live.inPort.close();
        } catch (Exception e) {
            fail(live.exchange, new OSCPortClosingError(OSCPort.In));
            return;
        }

        try {
            live.outPort.close();
        } catch (Exception e) {
            fail(live.exchange, new OSCPortClosingError(OSCPort.Out));
            return;
        }

        complete(live.exchange);
    }

    private static void handleNextRequest(LiveOSCExchange live) {
        if (hasRequest(live)) {
            OSCRequest request = OSCExchange.getRequest(live.exchange, live.requestIndex);
            live.requestIndex++;
            handleRequest(live, request);
        } else
            complete(live);
    }

    private static void handleRequest(LiveOSCExchange live, OSCRequest request) {
        if (request instanceof OSCSendRequest)
            handleSendRequest(live, (OSCSendRequest) request);
        else if (request instanceof OSCRawReceiveRequest)
            handleRawReceiveRequest(live, (OSCRawReceiveRequest) request);
        else if (request instanceof OSCParsedReceiveRequest)
            handleParsedReceiveRequest(live, (OSCParsedReceiveRequest<?>) request);
    }

    private static void handleSendRequest(LiveOSCExchange live, OSCSendRequest request) {
        try {
            OSCMessage message = OSCSendRequest.generateMessage(request);
            live.outPort.send(message);
            handleNextRequest(live);
        } catch (IOException e) {
            fail(live.exchange, new OSCPortUseError(OSCPort.Out));
        } catch (OSCSerializeException e) {
            fail(live.exchange, new OSCMessageError(request.args));
        }
    }

    private static void handleReceiveRequest(LiveOSCExchange live, OSCReceiveRequest request, OSCRawReceiveListener onValidated) {
        MessageSelector selector = new OSCPatternAddressMessageSelector(request.address.value);
        ForwardRef<OSCMessageListener> listener = new ForwardRef<>();

        listener.value = event -> {
            live.inPort.getDispatcher().removeListener(selector, listener.value);
            live.inPort.stopListening();

            OSCArgs receivedArgs = OSCArgs.multiple(event.getMessage().getArguments());

            if (request.validator != null)
                if (request.validator.isValid(receivedArgs)) {
                    onValidated.handle(receivedArgs);
                    handleNextRequest(live);
                } else
                    fail(live.exchange, new OSCValidationError(receivedArgs));
            else
                handleNextRequest(live);
        };

        live.inPort.getDispatcher().addListener(selector, listener.value);
        live.inPort.startListening();
    }

    private static void handleRawReceiveRequest(LiveOSCExchange live, OSCRawReceiveRequest request) {
        handleReceiveRequest(live, request, request.listener);
    }

    private static <T> void handleParsedReceiveRequest(LiveOSCExchange live, OSCParsedReceiveRequest<T> request) {
        handleReceiveRequest(live, request, args -> {
            Optional<T> parsed = request.parser.parse(args);

            if (parsed.isPresent())
                request.listener.handle(parsed.get());
            else
                fail(live.exchange, new OSCParsingError(args));
        });
    }


    private final OSCExchange exchange;
    private final OSCPortOut outPort;
    private final OSCPortIn inPort;
    private int requestIndex = 0;


    private LiveOSCExchange(OSCExchange exchange, OSCPortOut outPort, OSCPortIn inPort) {
        this.exchange = exchange;
        this.outPort = outPort;
        this.inPort = inPort;
    }

}
