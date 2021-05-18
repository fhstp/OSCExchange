package ac.at.fhstp.digitech.oscexchange;

final class OSCRawReceiveRequest extends OSCReceiveRequest {

    public final OSCRawReceiveListener listener;


    OSCRawReceiveRequest(OSCAddress address, OSCArgsValidator validator, OSCRawReceiveListener listener) {
        super(address, validator);
        this.listener = listener;
    }

    OSCRawReceiveRequest(OSCAddress address, OSCRawReceiveListener listener) {
        super(address);
        this.listener = listener;
    }

}
