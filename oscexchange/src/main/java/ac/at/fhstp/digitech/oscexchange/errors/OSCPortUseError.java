package ac.at.fhstp.digitech.oscexchange.errors;

import ac.at.fhstp.digitech.oscexchange.OSCPort;

/**
 * An error that occurs when an open port cannot be accessed
 */
public final class OSCPortUseError extends OSCPortError {

    public OSCPortUseError(Exception exception, OSCPort port) {
        super(exception, port);
    }

}
