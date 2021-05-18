package ac.at.fhstp.digitech.oscexchange;

final class OSCSendRequest extends OSCRequest {

    public final OSCArgs args;


    OSCSendRequest(OSCAddress address, OSCArgs args) {
        super(address);
        this.args = args;
    }

}
