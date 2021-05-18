package ac.at.fhstp.digitech.oscexchange;

abstract class OSCReceiveRequest extends OSCRequest {

    private final OSCArgsValidator validator;


    protected OSCReceiveRequest(OSCAddress address, OSCArgsValidator validator) {
        super(address);
        this.validator = validator;
    }

}
