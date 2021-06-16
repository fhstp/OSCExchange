package ac.at.fhstp.digitech.oscexchange.errors

import ac.at.fhstp.digitech.oscexchange.OSCPort

/**
 * An error that occurs when an open port cannot be accessed
 */
class OSCPortUseException(
    /**
     * The port that caused the issue
     */
    port: OSCPort,
    /**
     * A message describing the issue
     */
    message: String,
    /**
     * The cause of the exception
     */
    inner: Throwable?
) : OSCPortException(port, message, inner)