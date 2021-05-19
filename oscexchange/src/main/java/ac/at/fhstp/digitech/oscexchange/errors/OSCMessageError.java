package ac.at.fhstp.digitech.oscexchange.errors;

import ac.at.fhstp.digitech.oscexchange.OSCArgs;

/**
 * An error that occurs when trying to construct an OSC-message to send
 */
public final class OSCMessageError extends OSCArgsError {

    public OSCMessageError(Exception exception, OSCArgs args) {
        super(exception, args);
    }

}
