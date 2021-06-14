package ac.at.fhstp.digitech.oscexchange.errors

import ac.at.fhstp.digitech.oscexchange.OSCArgs

/**
 * An error that occurs when OSCArgs fail to be validated
 */
class OSCValidationError(exception: Exception?, args: OSCArgs) :
    OSCArgsError(exception, args)