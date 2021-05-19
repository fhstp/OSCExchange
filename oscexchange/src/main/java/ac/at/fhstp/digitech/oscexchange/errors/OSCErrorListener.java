package ac.at.fhstp.digitech.oscexchange.errors;

/**
 * A listener for when an error occurs
 */
@FunctionalInterface
public interface OSCErrorListener {

    /**
     * Handles the occurred error
     *
     * @param error The error that occurred
     */
    void handle(OSCError error);

}
