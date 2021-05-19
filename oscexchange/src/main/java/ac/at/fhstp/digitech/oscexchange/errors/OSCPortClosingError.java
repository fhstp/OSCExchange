package ac.at.fhstp.digitech.oscexchange.errors;

import ac.at.fhstp.digitech.oscexchange.OSCPort;

public final class OSCPortClosingError extends OSCPortError {

    public OSCPortClosingError(Exception exception, OSCPort port) {
        super(exception, port);
    }

}
