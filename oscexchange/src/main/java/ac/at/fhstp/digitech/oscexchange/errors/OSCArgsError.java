package ac.at.fhstp.digitech.oscexchange.errors;

import ac.at.fhstp.digitech.oscexchange.OSCArgs;

public abstract class OSCArgsError extends OSCError {

    public final OSCArgs args;


    protected OSCArgsError(Exception exception, OSCArgs args) {
        super(exception);
        this.args = args;
    }
}
