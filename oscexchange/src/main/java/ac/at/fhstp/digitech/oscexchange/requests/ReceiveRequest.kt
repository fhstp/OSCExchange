package ac.at.fhstp.digitech.oscexchange.requests

import ac.at.fhstp.digitech.oscexchange.OSCAddress
import ac.at.fhstp.digitech.oscexchange.OSCArgs
import ac.at.fhstp.digitech.oscexchange.PublicApi
import ac.at.fhstp.digitech.oscexchange.errors.OSCError
import ac.at.fhstp.digitech.oscexchange.errors.OSCValidationError

data class ReceiveRequest(
    val address: OSCAddress,
    val onError: (OSCError) -> Unit,
    val onReceived: (OSCArgs) -> Unit
) : Request {

    companion object {

        private val noReceiveHandling = { _: OSCArgs -> }

        private val noValidation = { _: OSCArgs -> true }


        @PublicApi
        fun new(address: OSCAddress) =
            ReceiveRequestBuilder(
                address, noReceiveHandling, null,
                noValidation, Request.noErrorHandling
            )

    }

}

data class ReceiveRequestBuilder(
    private val address: OSCAddress,
    private val onReceived: (OSCArgs) -> Unit,
    private val parser: RequestParser<*>?,
    private val validator: (OSCArgs) -> Boolean,
    private val onError: (OSCError) -> Unit
) : RequestBuilder<ReceiveRequest> {

    override fun build(): ReceiveRequest =
        ReceiveRequest(address, onError, buildReceiveHandler())

    private fun buildReceiveHandler() =
        { args: OSCArgs ->
            if (validator(args)) {
                onReceived(args)

                parser?.tryParse(args, onError)
            } else
                onError(OSCValidationError(args))
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
        withParser(parser = RequestParser(parser, onParseSuccess))

    @PublicApi
    fun withParser(parser: RequestParser<*>) =
        copy(parser = parser)

    @PublicApi
    fun withValidator(validator: (OSCArgs) -> Boolean) =
        copy(validator = validator)

    @PublicApi
    fun onError(onError: (OSCError) -> Unit) =
        copy(onError = onError)

}