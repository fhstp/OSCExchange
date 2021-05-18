package ac.at.fhstp.digitech.oscexchange;

public class OSCParseException extends Exception {

    public final OSCArgs args;

    public OSCParseException(OSCArgs args) {
        super("Could not parse OSC args!");
        this.args = args;
    }
}
