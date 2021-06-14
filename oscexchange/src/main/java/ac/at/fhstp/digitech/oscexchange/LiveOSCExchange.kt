package ac.at.fhstp.digitech.oscexchange

import ac.at.fhstp.digitech.oscexchange.errors.*
import com.illposed.osc.*
import com.illposed.osc.messageselector.OSCPatternAddressMessageSelector
import com.illposed.osc.transport.udp.OSCPortIn
import com.illposed.osc.transport.udp.OSCPortOut
import java.io.IOException
import java.net.InetSocketAddress
import java.util.*

internal class LiveOSCExchange private constructor(
    private val exchange: OSCExchange,
    private val outPort: OSCPortOut,
    private val inPort: OSCPortIn
) {
    private var requestIndex = 0

    companion object {

        fun start(exchange: OSCExchange, devicePair: OSCDevicePair) {
            val outPort = tryGenerateOutPort(devicePair.remote)
            val inPort = tryGenerateInPort(devicePair.local)

            if (!outPort.isPresent)
                fail(
                    exchange, OSCPortOpeningError(null, OSCPort.Out)
                ) else if (!inPort.isPresent)
                fail(
                    exchange, OSCPortOpeningError(null, OSCPort.In)
                ) else run(
                LiveOSCExchange(
                    exchange,
                    outPort.get(),
                    inPort.get()
                )
            )
        }

        private fun fail(exchange: OSCExchange, error: OSCError) {
            if (exchange.errorListener != null)
                exchange.errorListener.handle(error)
        }

        private fun complete(exchange: OSCExchange) {
            if (exchange.completeListener != null)
                exchange.completeListener.handle()
        }

        private fun tryGenerateOutPort(address: InetSocketAddress?): Optional<OSCPortOut> {
            return try {
                Optional.of(OSCPortOut(address))
            } catch (e: Exception) {
                Optional.empty()
            }
        }

        private fun tryGenerateInPort(address: InetSocketAddress?): Optional<OSCPortIn> {
            return try {
                val port = OSCPortIn(address)
                Optional.of(port)
            } catch (e: Exception) {
                Optional.empty()
            }
        }

        private fun run(live: LiveOSCExchange) {
            handleNextRequest(live)
        }

        private fun hasRequest(live: LiveOSCExchange): Boolean {
            return live.requestIndex < OSCExchange.getRequestCount(
                live.exchange
            )
        }

        private fun complete(live: LiveOSCExchange) {
            try {
                live.inPort.close()
            } catch (e: Exception) {
                fail(live.exchange, OSCPortClosingError(e, OSCPort.In))
                return
            }
            try {
                live.outPort.close()
            } catch (e: Exception) {
                fail(live.exchange, OSCPortClosingError(e, OSCPort.Out))
                return
            }
            complete(live.exchange)
        }

        private fun handleNextRequest(live: LiveOSCExchange) {
            if (hasRequest(live)) {
                val request: OSCRequest = OSCExchange.getRequest(
                    live.exchange,
                    live.requestIndex
                )
                live.requestIndex++
                handleRequest(live, request)
            } else complete(live)
        }

        private fun handleRequest(live: LiveOSCExchange, request: OSCRequest) {
            when (request) {
                is OSCSendRequest ->
                    handleSendRequest(live, request)
                is OSCRawReceiveRequest ->
                    handleRawReceiveRequest(live, request)
                is OSCParsedReceiveRequest<*> ->
                    handleParsedReceiveRequest(live, request)
            }
        }

        private fun handleSendRequest(
            live: LiveOSCExchange,
            request: OSCSendRequest
        ) {
            try {
                val message: OSCMessage =
                    OSCSendRequest.generateMessage(request)
                live.outPort.send(message)
                handleNextRequest(live)
            } catch (e: IOException) {
                fail(live.exchange, OSCPortUseError(e, OSCPort.Out))
            } catch (e: OSCSerializeException) {
                fail(live.exchange, OSCMessageError(e, request.args))
            }
        }

        private fun handleReceiveRequest(
            live: LiveOSCExchange,
            request: OSCReceiveRequest,
            onValidated: OSCRawReceiveListener
        ) {
            val selector: MessageSelector =
                OSCPatternAddressMessageSelector(request.address.value)
            val listener = ForwardRef<OSCMessageListener>()
            listener.value = OSCMessageListener { event: OSCMessageEvent ->
                live.inPort.dispatcher.removeListener(selector, listener.value)
                live.inPort.stopListening()
                val receivedArgs: OSCArgs =
                    OSCArgs.list(event.message.arguments)
                if (request.validator != null) if (request.validator.isValid(
                        receivedArgs
                    )
                ) {
                    onValidated.handle(receivedArgs)
                    handleNextRequest(live)
                } else fail(
                    live.exchange,
                    OSCValidationError(null, receivedArgs)
                ) else handleNextRequest(live)
            }
            live.inPort.dispatcher.addListener(selector, listener.value)
            live.inPort.startListening()
        }

        private fun handleRawReceiveRequest(
            live: LiveOSCExchange,
            request: OSCRawReceiveRequest
        ) {
            handleReceiveRequest(
                live,
                request
            ) { args: OSCArgs ->
                if (request.listener != null)
                    request.listener.handle(args)
            }
        }

        private fun <T> handleParsedReceiveRequest(
            live: LiveOSCExchange,
            request: OSCParsedReceiveRequest<T>
        ) {
            handleReceiveRequest(live, request) { args: OSCArgs ->
                val parsed = request.parser.parse(args)
                if (parsed.isPresent) {
                    if (request.listener != null)
                        request.listener.handle(parsed.get())
                } else
                    fail(live.exchange, OSCParsingError(null, args))
            }
        }
    }
}