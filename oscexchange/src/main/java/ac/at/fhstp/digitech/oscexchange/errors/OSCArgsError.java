package ac.at.fhstp.digitech.oscexchange.errors;

import ac.at.fhstp.digitech.oscexchange.OSCArgs;

/**
 * Base-class for all errors that concern invalid arguments
 */
public abstract class OSCArgsError extends OSCError {

    /**
     * The arguments that caused the problem
     */
    public final OSCArgs args;


    protected OSCArgsError(Exception exception, OSCArgs args) {
        super(exception);
        this.args = args;
    }
}
