package ac.at.fhstp.digitech.oscexchange.errors;

import ac.at.fhstp.digitech.oscexchange.OSCPort;

/**
 * An error that occurs when an open port cannot be closed
 */
public final class OSCPortClosingError extends OSCPortError {

    public OSCPortClosingError(Exception exception, OSCPort port) {
        super(exception, port);
    }

}
