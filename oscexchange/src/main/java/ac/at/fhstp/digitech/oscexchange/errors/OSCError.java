package ac.at.fhstp.digitech.oscexchange.errors;

import ac.at.fhstp.digitech.oscexchange.PublicApi;

/**
 * Base-class for all errors that can occur during an OSCExchange
 */
public abstract class OSCError {

    /**
     * The exception that occurred. Null if no exception occurred
     */
    @PublicApi
    public final Exception exception;


    protected OSCError(Exception exception) {
        this.exception = exception;
    }
}
