package ac.at.fhstp.digitech.oscexchange

import android.util.Log

/**
 * Function that handles a successful request
 */
typealias SuccessHandler = () -> Unit

/**
 * Contains some common success-handlers
 */
object SuccessHandlers {

    /**
     * Does nothing
     */
    @PublicApi
    val noSuccessHandling: SuccessHandler =
        {}


    /**
     * Prints a message to System.out
     *
     * @param message The message to print
     * @return The created success handler
     */
    @PublicApi
    fun print(message: String): SuccessHandler =
        { println(message) }

    /**
     * Prints a message to the log
     *
     * @param tag The tag under which to print the message
     * @param message The message that should be printed
     * @return The created success handler
     */
    @PublicApi
    fun log(tag: String, message: String): SuccessHandler =
        { Log.i(tag, message) }

}