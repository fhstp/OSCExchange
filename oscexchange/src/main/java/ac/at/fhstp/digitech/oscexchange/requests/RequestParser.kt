package ac.at.fhstp.digitech.oscexchange.requests

import ac.at.fhstp.digitech.oscexchange.OSCArgs
import ac.at.fhstp.digitech.oscexchange.errors.OSCParsingError

class RequestParser<TParsed>(
    private val parser: (OSCArgs) -> TParsed?,
    private val onParseSuccess: (TParsed) -> Unit
) {

    internal fun tryParse(
        args: OSCArgs,
        onError: (OSCParsingError) -> Unit
    ) {
        val parsed = this.parser(args)

        if (parsed != null)
            this.onParseSuccess(parsed)
        else
            onError(OSCParsingError(args))
    }

}