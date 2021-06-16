package ac.at.fhstp.digitech.oscexchange.errors

import ac.at.fhstp.digitech.oscexchange.OSCPort
import ac.at.fhstp.digitech.oscexchange.PublicApi

/**
 * Base-class for all errors that occur when a port cannot be used
 */
abstract class OSCPortException(
    /**
     * The port that caused the issue
     */
    @PublicApi val port: OSCPort,
    /**
     * A message describing the issue
     */
    message: String,
    /**
     * The cause of the exception
     */
    inner: Throwable?
) : OSCException(message, inner)