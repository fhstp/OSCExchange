package ac.at.fhstp.digitech.oscexchange

import com.illposed.osc.OSCMessage

internal class OSCSendRequest(address: OSCAddress, val args: OSCArgs) :
    OSCRequest(address) {

    companion object {

        fun generateMessage(sendRequest: OSCSendRequest): OSCMessage {
            return OSCMessage(
                sendRequest.address.value,
                OSCArgs.asList(sendRequest.args)
            )
        }

    }

}