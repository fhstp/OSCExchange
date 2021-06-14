package ac.at.fhstp.digitech.oscexchange

internal class OSCParsedReceiveRequest<T>(
    address: OSCAddress,
    validator: OSCArgsValidator?,
    val parser: OSCArgsParser<T>,
    val listener: OSCParsedReceiveListener<T>?
) : OSCReceiveRequest(address, validator)