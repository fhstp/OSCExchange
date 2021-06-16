package ac.at.fhstp.digitech.oscexchange

import ac.at.fhstp.digitech.oscexchange.requests.Request
import ac.at.fhstp.digitech.oscexchange.requests.RequestBuilder

/**
 * Stores all information about an OSC exchange.
 * Must be made runnable before it can be executed
 */
@PublicApi
class OSCExchange private constructor(private val requests: Array<Request>) {

    companion object {

        /**
         * Starts building a new OSC exchange
         */
        @PublicApi
        fun new() =
            Builder(listOf())

    }


    /**
     * Attempts to make this exchange runnable.
     * For this ports must be opened, which could fail
     * @param devicePair The pair between which the exchange should be run
     * @return A result which could contain the runnable exchange
     */
    @PublicApi
    fun tryMakeRunnable(devicePair: OSCDevicePair): Result<RunnableOSCExchange> {
        val inPortRes = devicePair.tryOpenInPort()
        if (inPortRes.isFailure)
            return Result.failure(inPortRes.exceptionOrNull()!!)

        val outPortRes = devicePair.tryOpenOutPort()
        if (outPortRes.isFailure)
            return Result.failure(outPortRes.exceptionOrNull()!!)

        return Result.success(
            RunnableOSCExchange(
                requests,
                inPortRes.getOrNull()!!,
                outPortRes.getOrNull()!!
            )
        )
    }

    /**
     * A builder class for OSCExchange
     */
    data class Builder(private val requests: List<Request>) {

        /**
         * Adds a new request to this exchange.
         * Requests are executed in the order they are added
         * @param requestBuilder A builder for a request
         */
        @PublicApi
        fun addRequest(requestBuilder: RequestBuilder<*>) =
            Builder(requests + requestBuilder.build())

        /**
         * Builds an OSCExchange from this builder
         * @return The built OSCExchange
         */
        @PublicApi
        fun build() =
            OSCExchange(requests.toTypedArray())

    }

}