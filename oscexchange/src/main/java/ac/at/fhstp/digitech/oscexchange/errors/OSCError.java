package ac.at.fhstp.digitech.oscexchange.errors;

/**
 * Base-class for all errors that can occur during an OSCExchange
 */
public abstract class OSCError {

    /**
     * The exception that occurred. Null if no exception occurred
     */
    public final Exception exception;


    protected OSCError(Exception exception) {
        this.exception = exception;
    }
}
