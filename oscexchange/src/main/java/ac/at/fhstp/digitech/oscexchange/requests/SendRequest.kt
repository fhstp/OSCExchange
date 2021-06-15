package ac.at.fhstp.digitech.oscexchange.requests

import ac.at.fhstp.digitech.oscexchange.OSCAddress
import ac.at.fhstp.digitech.oscexchange.OSCArgs
import ac.at.fhstp.digitech.oscexchange.PublicApi
import ac.at.fhstp.digitech.oscexchange.errors.OSCException
import com.illposed.osc.OSCMessage
import java.util.*

data class SendRequest(
    val address: OSCAddress,
    val argsGenerator: () -> OSCArgs,
    val onError: (OSCException) -> Unit,
    val onSuccess: () -> Unit
) : Request {

    companion object {

        private val emptyArgsGenerator = { OSCArgs.empty }

        private val noSuccessHandling = { }


        @PublicApi
        fun new(address: OSCAddress) =
            Builder(
                address, emptyArgsGenerator,
                Request.noErrorHandling, noSuccessHandling
            )

        @PublicApi
        fun new(address: String): Optional<Builder> =
            OSCAddress.tryCreate(address)
                .map { new(it) }

    }


    internal fun buildOSCMessage(args: OSCArgs) =
        OSCMessage(address.value, args.asList())


    data class Builder(
        private val address: OSCAddress,
        private val argsGenerator: () -> OSCArgs,
        private val onError: (OSCException) -> Unit,
        private val onSuccess: () -> Unit
    ) : RequestBuilder<SendRequest> {

        override fun build() =
            SendRequest(address, argsGenerator, onError, onSuccess)

        @PublicApi
        fun withArgs(args: OSCArgs) =
            withArgsGenerator(argsGenerator = { args })

        @PublicApi
        fun withArgsGenerator(argsGenerator: () -> OSCArgs) =
            copy(argsGenerator = argsGenerator)

        @PublicApi
        fun onError(onError: (OSCException) -> Unit) =
            copy(onError = onError)

        @PublicApi
        fun onSuccess(onSuccess: () -> Unit) =
            copy(onSuccess = onSuccess)

    }

}
