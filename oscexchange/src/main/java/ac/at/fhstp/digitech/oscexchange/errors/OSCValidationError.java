package ac.at.fhstp.digitech.oscexchange.errors;

import ac.at.fhstp.digitech.oscexchange.OSCArgs;

public final class OSCValidationError extends OSCArgsError {

    public OSCValidationError(Exception exception, OSCArgs args) {
        super(exception, args);
    }

}
