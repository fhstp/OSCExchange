package ac.at.fhstp.digitech.oscexchange;

import com.illposed.osc.OSCMessage;

final class OSCSendRequest extends OSCRequest {

    static OSCMessage generateMessage(OSCSendRequest sendRequest) {
        return new OSCMessage(sendRequest.address.value, OSCArgs.asList(sendRequest.args));
    }


    public final OSCArgs args;


    OSCSendRequest(OSCAddress address, OSCArgs args) {
        super(address);
        this.args = args;
    }

}
