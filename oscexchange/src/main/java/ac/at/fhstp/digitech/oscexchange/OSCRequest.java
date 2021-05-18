package ac.at.fhstp.digitech.oscexchange;

import java.util.Optional;

abstract class OSCRequest {

    public final OSCAddress address;
    private final OSCRequest next;


    protected OSCRequest(OSCAddress address, OSCRequest next) {
        this.address = address;
        this.next = next;
    }

    protected OSCRequest(OSCAddress address) {
        this.address = address;
        next = null;
    }


    protected Optional<OSCRequest> getNext() {
        return Optional.ofNullable(next);
    }

}
