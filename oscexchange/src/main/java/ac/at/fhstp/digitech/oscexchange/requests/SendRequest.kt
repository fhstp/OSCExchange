package ac.at.fhstp.digitech.oscexchange.requests

import ac.at.fhstp.digitech.oscexchange.*
import com.illposed.osc.OSCMessage
import java.util.*

data class SendRequest(
    val address: OSCAddress,
    val argsGenerator: ArgGenerator,
    val onError: ErrorHandler,
    val onSuccess: SuccessHandler
) : Request {

    companion object {

        @PublicApi
        fun new(address: OSCAddress) =
            Builder(
                address, ArgGenerators.empty,
                ErrorHandlers.noErrorHandling, SuccessHandlers.noSuccessHandling
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
        private val argsGenerator: ArgGenerator,
        private val onError: ErrorHandler,
        private val onSuccess: SuccessHandler
    ) : RequestBuilder<SendRequest> {

        override fun build() =
            SendRequest(address, argsGenerator, onError, onSuccess)

        @PublicApi
        fun withArgs(args: OSCArgs) =
            withArgsGenerator(ArgGenerators.constant(args))

        @PublicApi
        fun withArgsGenerator(argsGenerator: ArgGenerator) =
            copy(argsGenerator = argsGenerator)

        @PublicApi
        fun onError(onError: ErrorHandler) =
            copy(onError = onError)

        @PublicApi
        fun onSuccess(onSuccess: SuccessHandler) =
            copy(onSuccess = onSuccess)

    }

}
