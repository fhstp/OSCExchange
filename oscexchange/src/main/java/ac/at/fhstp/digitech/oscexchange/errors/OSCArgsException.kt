package ac.at.fhstp.digitech.oscexchange.errors

import ac.at.fhstp.digitech.oscexchange.OSCArgs
import ac.at.fhstp.digitech.oscexchange.PublicApi

/**
 * Base-class for all errors that concern invalid arguments
 */
abstract class OSCArgsException protected constructor(
    /**
     * The arguments that caused the error
     */
    @PublicApi val args: OSCArgs,
    /**
     * A message about the error
     */
    message: String,
    /**
     * The cause for this exception
     */
    inner: Throwable?
) : OSCException(message, inner)