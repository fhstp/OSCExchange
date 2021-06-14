package ac.at.fhstp.digitech.oscexchange

internal class OSCRawReceiveRequest(
    address: OSCAddress,
    validator: OSCArgsValidator?,
    val listener: OSCRawReceiveListener?
) : OSCReceiveRequest(address, validator)