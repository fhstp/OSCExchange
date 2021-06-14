package ac.at.fhstp.digitech.oscexchange.errors

import ac.at.fhstp.digitech.oscexchange.OSCPort

/**
 * An error that occurs when an open port cannot be accessed
 */
class OSCPortUseError(exception: Exception, port: OSCPort) :
    OSCPortError(exception, port)