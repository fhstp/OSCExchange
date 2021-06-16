package ac.at.fhstp.digitech.oscexchange

import ac.at.fhstp.digitech.oscexchange.errors.OSCException
import android.util.Log

typealias ErrorHandler = (OSCException) -> Unit

object ErrorHandlers {

    val noErrorHandling: ErrorHandler =
        {}

    val print: ErrorHandler =
        { error -> println(error.message) }

    fun log(tag: String): ErrorHandler =
        { error -> Log.e(tag, error.message, error.cause) }

}