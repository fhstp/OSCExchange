package ac.at.fhstp.digitech.oscexchange

import ac.at.fhstp.digitech.oscexchange.errors.OSCPortOpeningError
import com.illposed.osc.transport.udp.OSCPortIn
import com.illposed.osc.transport.udp.OSCPortOut
import java.net.InetSocketAddress

/**
 * Contains information about a pair of devices that communicate over OSC
 */
class OSCDevicePair @PublicApi constructor(
    /**
     * The address of this device
     */
    @PublicApi val local: InetSocketAddress,
    /**
     * The address of the other device
     */
    @PublicApi val remote: InetSocketAddress
) {

    fun tryOpenInPort() =
        try {
            Result.success(OSCPortIn(local))
        } catch (e: Exception) {
            val error = OSCPortOpeningError(
                OSCPort.In, "Could not open in port!", e
            )
            Result.failure(error)
        }

    fun tryOpenOutPort() =
        try {
            Result.success(OSCPortOut(remote))
        } catch (e: Exception) {
            val error = OSCPortOpeningError(
                OSCPort.Out, "Could not open out port!", e
            )
            Result.failure(error)
        }

}