package ac.at.fhstp.digitech.oscexchange;

@FunctionalInterface
public interface OSCParsedReceiveListener<T> {

    void handle(T parsed);

}
