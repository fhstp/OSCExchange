package ac.at.fhstp.digitech.oscexchange;

/**
 * Listener for when OSCArgs were received
 */
@FunctionalInterface
public interface OSCRawReceiveListener {

    /**
     * Handles the received OSCArgs
     *
     * @param args The received args
     */
    void handle(OSCArgs args);

}
