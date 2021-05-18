package ac.at.fhstp.digitech.oscexchange;

abstract class OSCRequest {

    public final OSCAddress address;


    protected OSCRequest(OSCAddress address) {
        this.address = address;
    }

}
