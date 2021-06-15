package ac.at.fhstp.digitech.oscexchange

import ac.at.fhstp.digitech.oscexchange.requests.Request
import ac.at.fhstp.digitech.oscexchange.requests.RequestBuilder

/**
 * Stores all information about an OSC exchange between two devices
 */
class OSCExchange private constructor(val requests: Array<Request>) {

    companion object {

        @PublicApi
        fun new() =
            Builder(listOf())

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