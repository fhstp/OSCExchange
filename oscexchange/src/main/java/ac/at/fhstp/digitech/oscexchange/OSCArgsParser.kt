package ac.at.fhstp.digitech.oscexchange

import java.util.*

/**
 * Parses OSCArgs to some type
 *
 * @param <T>The type to parse to
</T> */
fun interface OSCArgsParser<T> {
    /**
     * Attempts to parse the given OSCArgs to the parsers type
     *
     * @param args The OSCArgs that should be parsed
     * @return Optional parsed object. Empty if parsing was not successful
     */
    @PublicApi
    fun parse(args: OSCArgs): Optional<T>

    /**
     * Attempts to parse the given OSCArgs to the parsers type
     *
     * @param args The OSCArgs that should be parsed
     * @return The parsed object
     * @throws OSCParseException Thrown if parsing was not successful
     */
    @PublicApi
    @Throws(OSCParseException::class)
    fun forceParse(args: OSCArgs): T {
        return parse(args).orElseThrow { OSCParseException(args) }
    }
}