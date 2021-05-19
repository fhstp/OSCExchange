package ac.at.fhstp.digitech.oscexchange.errors;

import ac.at.fhstp.digitech.oscexchange.OSCPort;

public abstract class OSCPortError extends OSCError {

    public final OSCPort port;


    public OSCPortError(Exception exception, OSCPort port) {
        super(exception);
        this.port = port;
    }

}
