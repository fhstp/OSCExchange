package ac.at.fhstp.digitech.oscexchange.requests

import ac.at.fhstp.digitech.oscexchange.OSCAddress
import ac.at.fhstp.digitech.oscexchange.OSCArgs
import ac.at.fhstp.digitech.oscexchange.PublicApi
import ac.at.fhstp.digitech.oscexchange.errors.OSCException

data class SendRequest(
    val address: OSCAddress,
    val argsGenerator: (Unit) -> OSCArgs,
    val onError: (OSCException) -> Unit,
    val onSuccess: (Unit) -> Unit
) : Request {

    companion object {

        private val emptyArgsGenerator = { _: Unit -> OSCArgs.empty }

        private val noSuccessHandling = { _: Unit -> }


        @PublicApi
        fun new(address: OSCAddress) =
            Builder(
                address, emptyArgsGenerator,
                Request.noErrorHandling, noSuccessHandling
            )

        @PublicApi
        fun new(address: String) =
            new(OSCAddress.create(address).get())

    }

    data class Builder(
        private val address: OSCAddress,
        private val argsGenerator: (Unit) -> OSCArgs,
        private val onError: (OSCException) -> Unit,
        private val onSuccess: (Unit) -> Unit
    ) : RequestBuilder<SendRequest> {

        override fun build() =
            SendRequest(address, argsGenerator, onError, onSuccess)

        @PublicApi
        fun withArgs(args: OSCArgs) =
            withArgsGenerator(argsGenerator = { args })

        @PublicApi
        fun withArgsGenerator(argsGenerator: (Unit) -> OSCArgs) =
            copy(argsGenerator = argsGenerator)

        @PublicApi
        fun onError(onError: (OSCException) -> Unit) =
            copy(onError = onError)

        @PublicApi
        fun onSuccess(onSuccess: (Unit) -> Unit) =
            copy(onSuccess = onSuccess)

    }

}
