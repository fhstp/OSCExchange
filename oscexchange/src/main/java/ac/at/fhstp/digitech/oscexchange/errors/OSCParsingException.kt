package ac.at.fhstp.digitech.oscexchange.errors

import ac.at.fhstp.digitech.oscexchange.OSCArgs

/**
 * An error that occurs when OSCArgs cannot be parsed
 */
class OSCParsingException(args: OSCArgs, message: String, inner: Throwable) :
    OSCArgsException(args, message, inner)