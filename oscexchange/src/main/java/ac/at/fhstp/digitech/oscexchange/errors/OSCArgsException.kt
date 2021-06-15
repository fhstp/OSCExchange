package ac.at.fhstp.digitech.oscexchange.errors

import ac.at.fhstp.digitech.oscexchange.OSCArgs
import ac.at.fhstp.digitech.oscexchange.PublicApi

/**
 * Base-class for all errors that concern invalid arguments
 */
abstract class OSCArgsException protected constructor(
    @PublicApi val args: OSCArgs,
    message: String,
    inner: Throwable?
) : OSCException(message, inner)