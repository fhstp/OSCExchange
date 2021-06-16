package ac.at.fhstp.digitech.oscexchange

import ac.at.fhstp.digitech.oscexchange.errors.OSCException
import android.util.Log

/**
 * Function that handles an OSCException
 */
typealias ErrorHandler = (OSCException) -> Unit

/**
 * Contains some universal error-handlers
 */
object ErrorHandlers {

    /**
     * An error handler that does nothing
     */
    val noErrorHandling: ErrorHandler =
        {}

    /**
     * An error handler that prints the error to System.out
     */
    val print: ErrorHandler =
        { error -> println(error.message) }


    /**
     * Creates an error handler that logs the error
     *
     * @param tag The tag under which to log the message
     * @return The created error handler
     */
    fun log(tag: String): ErrorHandler =
        { error -> Log.e(tag, error.message, error.cause) }

}