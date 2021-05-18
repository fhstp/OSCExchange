package ac.at.fhstp.digitech.oscexchange;

final class OSCSendRequest extends OSCRequest {

    public final OSCArgs args;


    OSCSendRequest(OSCAddress address, OSCArgs args, OSCRequest next) {
        super(address, next);
        this.args = args;
    }

    OSCSendRequest(OSCAddress address, OSCArgs args) {
        super(address);
        this.args = args;
    }

}
