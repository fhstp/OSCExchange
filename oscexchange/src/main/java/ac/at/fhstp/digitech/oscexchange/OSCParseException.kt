package ac.at.fhstp.digitech.oscexchange

/**
 * An exception that occurs when invalid OSCArgs are force-parsed
 */
class OSCParseException(
    /**
     * The args that were attempted to parse
     */
    @PublicApi val args: OSCArgs
) : Exception("Could not parse OSC args!")