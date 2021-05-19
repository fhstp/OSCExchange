package ac.at.fhstp.digitech.oscexchange;

import java.util.Optional;

/**
 * Parses OSCArgs to some type
 *
 * @param <T>The type to parse to
 */
@FunctionalInterface
public interface OSCArgsParser<T> {

    /**
     * Attempts to parse the given OSCArgs to the parsers type
     *
     * @param args The OSCArgs that should be parsed
     * @return Optional parsed object. Empty if parsing was not successful
     */
    Optional<T> parse(OSCArgs args);
    
    /**
     * Attempts to parse the given OSCArgs to the parsers type
     *
     * @param args The OSCArgs that should be parsed
     * @return The parsed object
     * @throws OSCParseException Thrown if parsing was not successful
     */
    default T forceParse(OSCArgs args) throws OSCParseException {
        return parse(args).orElseThrow(() -> new OSCParseException(args));
    }

}
