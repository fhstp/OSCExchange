package ac.at.fhstp.digitech.oscexchange.errors

import ac.at.fhstp.digitech.oscexchange.OSCArgs

/**
 * An error that occurs when trying to construct an OSC-message to send
 */
class OSCMessageException(
    args: OSCArgs,
    message: String,
    inner: Throwable
) : OSCArgsException(args, message, inner)