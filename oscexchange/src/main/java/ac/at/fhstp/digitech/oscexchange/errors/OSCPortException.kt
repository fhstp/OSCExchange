package ac.at.fhstp.digitech.oscexchange.errors

import ac.at.fhstp.digitech.oscexchange.OSCPort
import ac.at.fhstp.digitech.oscexchange.PublicApi

/**
 * Base-class for all errors that occur when a port cannot be used
 */
abstract class OSCPortException(
    @PublicApi val port: OSCPort,
    message: String,
    inner: Throwable?
) : OSCException(message, inner)