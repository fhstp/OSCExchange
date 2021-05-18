package ac.at.fhstp.digitech.oscexchange.errors;

import ac.at.fhstp.digitech.oscexchange.OSCPort;

public final class OSCPortUseError extends OSCPortError {

    public OSCPortUseError(OSCPort port) {
        super(port);
    }

}
