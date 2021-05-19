package ac.at.fhstp.digitech.oscexchange;

/**
 * A listener for when an an exchange is completed
 */
@FunctionalInterface
public interface OSCExchangeCompleteListener {

    /**
     * Handles the completed exchange
     */
    void handle();

}
