package ac.at.fhstp.digitech.oscexchange;

import java.util.Optional;

@FunctionalInterface
public interface OSCArgsParser<T> {

    Optional<T> parse(OSCArgs args);


    default T forceParse(OSCArgs args) throws OSCParseException {
        return parse(args).orElseThrow(() -> new OSCParseException(args));
    }

}
