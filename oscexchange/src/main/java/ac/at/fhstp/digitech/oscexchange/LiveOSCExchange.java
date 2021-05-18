package ac.at.fhstp.digitech.oscexchange;

import com.illposed.osc.transport.udp.OSCPortIn;
import com.illposed.osc.transport.udp.OSCPortOut;

import java.net.InetSocketAddress;
import java.util.Optional;

class LiveOSCExchange {

    public static Optional<LiveOSCExchange> tryStart(OSCExchange exchange, OSCDevicePair devicePair) {
        Optional<OSCPortOut> outPort = tryGenerateOutPort(devicePair.remote);
        Optional<OSCPortIn> inPort = tryGenerateInPort(devicePair.local);

        if (outPort.isPresent() && inPort.isPresent())
            return Optional.of(new LiveOSCExchange(exchange, outPort.get(), inPort.get()));
        else
            return Optional.empty();
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


    private final OSCExchange exchange;
    private final OSCPortOut outPort;
    private final OSCPortIn inPort;


    private LiveOSCExchange(OSCExchange exchange, OSCPortOut outPort, OSCPortIn inPort) {
        this.exchange = exchange;
        this.outPort = outPort;
        this.inPort = inPort;
    }

}
