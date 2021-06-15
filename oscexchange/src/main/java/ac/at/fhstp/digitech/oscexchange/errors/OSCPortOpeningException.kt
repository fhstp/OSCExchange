package ac.at.fhstp.digitech.oscexchange.errors

import ac.at.fhstp.digitech.oscexchange.OSCPort

/**
 * An error that occurs when a closed port cannot be opened
 */
class OSCPortOpeningException(
    port: OSCPort,
    message: String,
    inner: Throwable?
) : OSCPortException(port, message, inner)