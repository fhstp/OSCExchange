package ac.at.fhstp.digitech.oscexchange

import ac.at.fhstp.digitech.oscexchange.errors.OSCException
import android.content.Context
import android.util.Log
import android.widget.Toast

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
    @PublicApi
    val noErrorHandling: ErrorHandler =
        {}

    /**
     * An error handler that prints the error to System.out
     */
    @PublicApi
    val print: ErrorHandler =
        { error -> println(error.message) }


    /**
     * Creates an error handler that logs the error
     *
     * @param tag The tag under which to log the message
     * @return The created error handler
     */
    @PublicApi
    fun log(tag: String): ErrorHandler =
        { error -> Log.e(tag, error.message, error.cause) }

    /**
     * Creates an error handler that toasts the error
     *
     * @param context The context under which to create the toast
     * @param length The length for how long to show the toast. This is a Toast.Duration
     * @return The created error handler
     */
    @PublicApi
    fun toast(
        context: Context,
        length: Int = Toast.LENGTH_SHORT
    ): ErrorHandler =
        { error -> Toast.makeText(context, error.message, length).show() }

}