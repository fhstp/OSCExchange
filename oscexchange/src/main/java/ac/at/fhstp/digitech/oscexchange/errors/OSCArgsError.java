package ac.at.fhstp.digitech.oscexchange.errors;

import ac.at.fhstp.digitech.oscexchange.OSCArgs;
import ac.at.fhstp.digitech.oscexchange.PublicApi;

/**
 * Base-class for all errors that concern invalid arguments
 */
public abstract class OSCArgsError extends OSCError {

    /**
     * The arguments that caused the problem
     */
    @PublicApi
    public final OSCArgs args;


    protected OSCArgsError(Exception exception, OSCArgs args) {
        super(exception);
        this.args = args;
    }
}
