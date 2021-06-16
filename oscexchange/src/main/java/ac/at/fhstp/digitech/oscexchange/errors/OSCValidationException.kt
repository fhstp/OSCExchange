package ac.at.fhstp.digitech.oscexchange.errors

import ac.at.fhstp.digitech.oscexchange.OSCArgs

/**
 * An error that occurs when OSCArgs fail to be validated
 */
class OSCValidationException(
    /**
     * The arguments that caused the error
     */
    args: OSCArgs,
    /**
     * A message about the error
     */
    message: String,
    /**
     * The cause for this exception
     */
    inner: Throwable?
) : OSCArgsException(args, message, inner)