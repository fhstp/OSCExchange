package ac.at.fhstp.digitech.oscexchange.requests

import ac.at.fhstp.digitech.oscexchange.OSCArgs
import ac.at.fhstp.digitech.oscexchange.errors.OSCParsingException

class RequestParser<TParsed>(
    private val parser: (OSCArgs) -> TParsed?,
    private val onParseSuccess: (TParsed) -> Unit
) {

    internal fun tryParse(
        args: OSCArgs,
        onError: (OSCParsingException) -> Unit
    ) {
        val parsed = this.parser(args)

        if (parsed != null)
            this.onParseSuccess(parsed)
        else
            onError(
                OSCParsingException(
                    args,
                    "Received args could not be parsed!",
                    null
                )
            )
    }

}