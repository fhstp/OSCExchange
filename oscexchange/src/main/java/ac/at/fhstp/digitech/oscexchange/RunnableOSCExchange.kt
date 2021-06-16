package ac.at.fhstp.digitech.oscexchange

import ac.at.fhstp.digitech.oscexchange.errors.OSCMessageException
import ac.at.fhstp.digitech.oscexchange.errors.OSCPortClosingException
import ac.at.fhstp.digitech.oscexchange.errors.OSCPortUseException
import ac.at.fhstp.digitech.oscexchange.requests.ReceiveRequest
import ac.at.fhstp.digitech.oscexchange.requests.Request
import ac.at.fhstp.digitech.oscexchange.requests.SendRequest
import com.illposed.osc.OSCMessageListener
import com.illposed.osc.OSCSerializeException
import com.illposed.osc.messageselector.OSCPatternAddressMessageSelector
import com.illposed.osc.transport.udp.OSCPortIn
import com.illposed.osc.transport.udp.OSCPortOut
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

/**
 * An OSCExchange that can be run
 */
class RunnableOSCExchange(
    private val requests: Array<Request>,
    private val inPort: OSCPortIn,
    private val outPort: OSCPortOut
) {

    /**
     * Runs the exchange
     */
    @PublicApi
    suspend fun run() {
        return withContext(Dispatchers.IO) {
            runRequestAtIndex(0)
        }
    }

    private fun runRequestAtIndex(index: Int) {
        val request = requests[index]
        val onContinue = {
            if (index < requests.size - 1)
                runRequestAtIndex(index + 1)
            else
                completeExchange()
        }
        runRequest(request, onContinue)
    }

    private fun runRequest(request: Request, onContinue: () -> Unit) {
        when (request) {
            is SendRequest -> runSendRequest(request, onContinue)
            is ReceiveRequest -> runReceiveRequest(request, onContinue)
            else -> throw IllegalArgumentException("Invalid request type!")
        }
    }

    private fun runSendRequest(
        request: SendRequest,
        onContinue: () -> Unit
    ) {
        val args = request.argsGenerator()
        val message = request.buildOSCMessage(args)
        try {
            outPort.send(message)
            request.onSuccess()
        } catch (e: OSCSerializeException) {
            request.onError(
                OSCMessageException(
                    args, "Could not serialize args!", e
                )
            )
        } catch (e: IOException) {
            request.onError(
                OSCPortUseException(
                    OSCPort.Out, "Could not use out port!", e
                )
            )
        } finally {
            // TODO: Add logic to only continue if specified in request
            onContinue()
        }
    }

    private fun runReceiveRequest(
        request: ReceiveRequest,
        onContinue: () -> Unit
    ) {
        val selector = OSCPatternAddressMessageSelector(request.address.value)
        val listener = ForwardRef<OSCMessageListener>()

        listener.value = OSCMessageListener {
            inPort.dispatcher.removeListener(selector, listener.value)
            inPort.stopListening()

            val args = OSCArgs.ofList(it.message.arguments)
            request.onReceived(args)

            // TODO: Allow config to only continue if no errors occurred
            onContinue()
        }

        inPort.dispatcher.addListener(selector, listener.value)
        inPort.startListening()
    }

    private fun completeExchange() {
        try {
            outPort.close()
        } catch (e: Exception) {
            throw OSCPortClosingException(
                OSCPort.Out, "Could not close out port", e
            )
        }

        try {
            inPort.close()
        } catch (e: Exception) {
            throw OSCPortClosingException(
                OSCPort.In, "Could not close in port", e
            )
        }
    }

}