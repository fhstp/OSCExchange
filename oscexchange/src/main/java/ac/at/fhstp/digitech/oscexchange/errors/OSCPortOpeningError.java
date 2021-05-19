package ac.at.fhstp.digitech.oscexchange.errors;

import ac.at.fhstp.digitech.oscexchange.OSCPort;

/**
 * An error that occurs when a closed port cannot be opened
 */
public final class OSCPortOpeningError extends OSCPortError {

    public OSCPortOpeningError(Exception exception, OSCPort port) {
        super(exception, port);
    }

}
