package ac.at.fhstp.digitech.oscexchange.errors

import ac.at.fhstp.digitech.oscexchange.OSCPort

/**
 * An error that occurs when an open port cannot be accessed
 */
class OSCPortUseException(
    port: OSCPort,
    message: String,
    inner: Throwable
) : OSCPortException(port, message, inner)