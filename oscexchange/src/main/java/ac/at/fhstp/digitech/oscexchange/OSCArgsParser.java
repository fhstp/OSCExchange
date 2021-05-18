package ac.at.fhstp.digitech.oscexchange;

import java.util.Optional;

@FunctionalInterface
public interface OSCArgsParser<T> {

    Optional<T> parse(OSCArgs args);


    default T forceParse(OSCArgs args) throws OSCParseException {
        Optional<T> resultOpt = parse(args);

        if (resultOpt.isPresent()) return resultOpt.get();
        else throw new OSCParseException(args);
    }

}
