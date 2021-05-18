package ac.at.fhstp.digitech.oscexchange;

abstract class OSCReceiveRequest extends OSCRequest {

    public final OSCArgsValidator validator;


    protected OSCReceiveRequest(OSCAddress address, OSCArgsValidator validator) {
        super(address);
        this.validator = validator;
    }

    protected OSCReceiveRequest(OSCAddress address) {
        super(address);
        this.validator = null;
    }

}
