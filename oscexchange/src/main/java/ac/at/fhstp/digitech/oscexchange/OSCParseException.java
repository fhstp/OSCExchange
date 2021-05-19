package ac.at.fhstp.digitech.oscexchange;

/**
 * An exception that occurs when invalid OSCArgs are force-parsed
 */
public final class OSCParseException extends Exception {

    /**
     * The args that were attempted to parse
     */
    public final OSCArgs args;

    public OSCParseException(OSCArgs args) {
        super("Could not parse OSC args!");
        this.args = args;
    }
}
