package ac.at.fhstp.digitech.oscexchange.errors;

import ac.at.fhstp.digitech.oscexchange.OSCArgs;

/**
 * An error that occurs when OSCArgs cannot be parsed
 */
public final class OSCParsingError extends OSCArgsError {

    public OSCParsingError(Exception exception, OSCArgs args) {
        super(exception, args);
    }

}
