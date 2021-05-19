package ac.at.fhstp.digitech.oscexchange.errors;

import ac.at.fhstp.digitech.oscexchange.OSCArgs;

public final class OSCMessageError extends OSCArgsError {

    public OSCMessageError(Exception exception, OSCArgs args) {
        super(exception, args);
    }

}
