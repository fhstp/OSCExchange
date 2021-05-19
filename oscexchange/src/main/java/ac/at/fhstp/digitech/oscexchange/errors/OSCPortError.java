package ac.at.fhstp.digitech.oscexchange.errors;

import ac.at.fhstp.digitech.oscexchange.OSCPort;

/**
 * Base-class for all errors that occur when a port cannot be used
 */
public abstract class OSCPortError extends OSCError {

    /**
     * Specifies whether the in- or out-port was the problem
     */
    public final OSCPort port;


    public OSCPortError(Exception exception, OSCPort port) {
        super(exception);
        this.port = port;
    }

}
