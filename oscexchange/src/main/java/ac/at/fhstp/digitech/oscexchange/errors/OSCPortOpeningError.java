package ac.at.fhstp.digitech.oscexchange.errors;

import ac.at.fhstp.digitech.oscexchange.OSCPort;

public final class OSCPortOpeningError extends OSCPortError {

    public OSCPortOpeningError(OSCPort port) {
        super(port);
    }

}
