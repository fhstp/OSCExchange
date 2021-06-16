package ac.at.fhstp.digitech.oscexchange

import ac.at.fhstp.digitech.oscexchange.requests.Request
import ac.at.fhstp.digitech.oscexchange.requests.RequestBuilder

/**
 * Stores all information about an OSC exchange between two devices
 */
class OSCExchange private constructor(private val requests: Array<Request>) {

    companion object {

        @PublicApi
        fun new() =
            Builder(listOf())

    }


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


    data class Builder(private val requests: List<Request>) {

        @PublicApi
        fun addRequest(requestBuilder: RequestBuilder<*>) =
            Builder(requests + requestBuilder.build())

        @PublicApi
        fun build() =
            OSCExchange(requests.toTypedArray())

    }

}