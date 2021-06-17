package ac.at.fhstp.digitech.oscexchange.requests

import ac.at.fhstp.digitech.oscexchange.*
import ac.at.fhstp.digitech.oscexchange.errors.OSCValidationException
import java.util.*

data class ReceiveRequest(
    val address: OSCAddress,
    val onError: ErrorHandler,
    val onReceived: (OSCArgs) -> Unit,
    val timeout: Long?
) : Request {

    companion object {

        @PublicApi
        const val DEFAULT_TIMEOUT = 5000L

        private val noReceiveHandling = { _: OSCArgs -> }

        @PublicApi
        fun new(address: OSCAddress) =
            Builder(
                address,
                noReceiveHandling,
                null,
                ArgValidators.noValidation,
                ErrorHandlers.noErrorHandling,
                DEFAULT_TIMEOUT
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
        private val validator: ArgValidator,
        private val onError: ErrorHandler,
        private val timeout: Long?
    ) : RequestBuilder<ReceiveRequest> {

        override fun build(): ReceiveRequest =
            ReceiveRequest(address, onError, buildReceiveHandler(), timeout)

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
        fun <TParsed> withParser(
            parser: ArgParser<TParsed>,
            onParseSuccess: (TParsed) -> Unit
        ) =
            withParser(RequestParser(parser, onParseSuccess))

        @PublicApi
        fun withParser(parser: RequestParser<*>) =
            copy(parser = parser)

        @PublicApi
        fun withValidator(validator: (OSCArgs) -> Boolean) =
            copy(validator = validator)

        @PublicApi
        fun withTimeout(millis: Long) =
            copy(timeout = millis)

        @PublicApi
        fun withoutTimeout() =
            copy(timeout = null)

        @PublicApi
        fun onError(onError: ErrorHandler) =
            copy(onError = onError)

    }

}