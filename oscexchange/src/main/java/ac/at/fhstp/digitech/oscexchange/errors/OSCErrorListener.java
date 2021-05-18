package ac.at.fhstp.digitech.oscexchange.errors;

@FunctionalInterface
public interface OSCErrorListener {

    void handle(OSCError error);

}
