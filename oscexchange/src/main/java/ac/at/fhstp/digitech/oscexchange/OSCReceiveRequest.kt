package ac.at.fhstp.digitech.oscexchange

internal abstract class OSCReceiveRequest : OSCRequest {
    val validator: OSCArgsValidator?

    protected constructor(
        address: OSCAddress,
        validator: OSCArgsValidator?
    ) : super(address) {
        this.validator = validator
    }

    protected constructor(address: OSCAddress) : super(address) {
        validator = null
    }
}