package ac.at.fhstp.digitech.oscexchange.errors

import ac.at.fhstp.digitech.oscexchange.OSCArgs
import ac.at.fhstp.digitech.oscexchange.PublicApi

/**
 * Base-class for all errors that concern invalid arguments
 */
abstract class OSCArgsError protected constructor(
    exception: Exception?,
    /**
     * The arguments that caused the problem
     */
    @PublicApi val args: OSCArgs
) : OSCError(exception)