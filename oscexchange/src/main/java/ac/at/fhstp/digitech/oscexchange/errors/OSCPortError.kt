package ac.at.fhstp.digitech.oscexchange.errors

import ac.at.fhstp.digitech.oscexchange.OSCPort
import ac.at.fhstp.digitech.oscexchange.PublicApi

/**
 * Base-class for all errors that occur when a port cannot be used
 */
abstract class OSCPortError(
    exception: Exception?,
    /**
     * Specifies whether the in- or out-port was the problem
     */
    @PublicApi val port: OSCPort
) : OSCError(exception)