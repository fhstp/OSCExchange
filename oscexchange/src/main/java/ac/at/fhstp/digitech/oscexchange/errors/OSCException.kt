package ac.at.fhstp.digitech.oscexchange.errors

/**
 * Base-class for all errors that can occur during an OSCExchange
 */
abstract class OSCException(
    /**
     * A message about the error
     */
    message: String,
    /**
     * The cause for this exception
     */
    inner: Throwable?
) : Exception(message, inner)