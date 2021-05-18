package ac.at.fhstp.digitech.oscexchange;

@FunctionalInterface
public interface OSCRawReceiveListener {

    void handle(OSCArgs args);

}
