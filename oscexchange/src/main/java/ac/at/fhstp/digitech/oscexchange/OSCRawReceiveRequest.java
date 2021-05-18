package ac.at.fhstp.digitech.oscexchange;

final class OSCRawReceiveRequest extends OSCReceiveRequest {

    public final OSCRawReceiveListener listener;


    protected OSCRawReceiveRequest(OSCAddress address, OSCArgsValidator validator, OSCRawReceiveListener listener, OSCRequest next) {
        super(address, validator, next);
        this.listener = listener;
    }

    protected OSCRawReceiveRequest(OSCAddress address, OSCArgsValidator validator, OSCRawReceiveListener listener) {
        super(address, validator);
        this.listener = listener;
    }

}
