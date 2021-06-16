package ac.at.fhstp.digitech.oscexchange.requests

import ac.at.fhstp.digitech.oscexchange.*
import ac.at.fhstp.digitech.oscexchange.errors.OSCException
import ac.at.fhstp.digitech.oscexchange.errors.OSCValidationException
import java.util.*

data class ReceiveRequest(
    val address: OSCAddress,
    val onError: (OSCException) -> Unit,
    val onReceived: (OSCArgs) -> Unit
) : Request {

    companion object {

        private val noReceiveHandling = { _: OSCArgs -> }

        @PublicApi
        fun new(address: OSCAddress) =
            Builder(
                address, noReceiveHandling, null,
                Validators.noValidation, Request.noErrorHandling
            )

        @PublicApi
        fun new(address: String): Optional<Builder> =
            OSCAddress.tryCreate(address)
                .map { new(it) }

    }

    data class Builder(
        private val address: OSCAddress,
        private val onReceived: (OSCArgs) -> Unit,
        private val parser: RequestParser<*>?,
        private val validator: Validator,
        private val onError: (OSCException) -> Unit
    ) : RequestBuilder<ReceiveRequest> {

        override fun build(): ReceiveRequest =
            ReceiveRequest(address, onError, buildReceiveHandler())

        private fun buildReceiveHandler() =
            { args: OSCArgs ->
                if (validator(args)) {
                    onReceived(args)

                    parser?.tryParse(args, onError)
                } else
                    onError(
                        OSCValidationException(
                            args,
                            "Received arguments did not pass validation",
                            null
                        )
                    )
                Unit
            }

        @PublicApi
        fun onReceive(onReceived: (OSCArgs) -> Unit) =
            copy(onReceived = onReceived)

        @PublicApi
        fun <T> withParser(
            parser: (OSCArgs) -> T?,
            onParseSuccess: (T) -> Unit
        ) =
            withParser(RequestParser(parser, onParseSuccess))

        @PublicApi
        fun withParser(parser: RequestParser<*>) =
            copy(parser = parser)

        @PublicApi
        fun withValidator(validator: (OSCArgs) -> Boolean) =
            copy(validator = validator)

        @PublicApi
        fun onError(onError: (OSCException) -> Unit) =
            copy(onError = onError)

    }

}