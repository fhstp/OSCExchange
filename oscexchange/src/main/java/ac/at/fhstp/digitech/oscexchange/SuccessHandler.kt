package ac.at.fhstp.digitech.oscexchange

import android.util.Log

typealias SuccessHandler = () -> Unit

object SuccessHandlers {

    val noSuccessHandling: SuccessHandler =
        {}


    fun print(message: String): SuccessHandler =
        { println(message) }

    fun log(tag: String, message: String): SuccessHandler =
        { Log.i(tag, message) }

}