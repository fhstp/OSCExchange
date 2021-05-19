package ac.at.fhstp.digitech.oscexchange.errors;

public abstract class OSCError {

    public final Exception exception;


    protected OSCError(Exception exception) {
        this.exception = exception;
    }
}
