package ac.at.fhstp.digitech.oscexchange.errors

/**
 * Base-class for all errors that can occur during an OSCExchange
 */
abstract class OSCException(message: String, inner: Throwable) :
    Exception(message, inner)