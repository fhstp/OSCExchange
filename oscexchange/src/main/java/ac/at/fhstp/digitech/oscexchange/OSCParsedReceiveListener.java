package ac.at.fhstp.digitech.oscexchange;

/**
 * Listener for when a parsed object was received
 *
 * @param <T> The type of the object
 */
@FunctionalInterface
public interface OSCParsedReceiveListener<T> {

    /**
     * Handles the received object
     *
     * @param parsed The received object
     */
    void handle(T parsed);

}
