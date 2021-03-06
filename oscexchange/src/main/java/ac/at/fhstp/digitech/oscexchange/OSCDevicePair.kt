package ac.at.fhstp.digitech.oscexchange

import ac.at.fhstp.digitech.oscexchange.errors.OSCPortOpeningException
import android.os.Parcelable
import com.illposed.osc.transport.udp.OSCPortIn
import com.illposed.osc.transport.udp.OSCPortOut
import kotlinx.parcelize.Parcelize
import java.net.InetSocketAddress

/**
 * Contains information about a pair of devices that communicate over OSC
 */
@Parcelize
class OSCDevicePair @PublicApi constructor(
    /**
     * The address of this device
     */
    @PublicApi val local: InetSocketAddress,
    /**
     * The address of the other device
     */
    @PublicApi val remote: InetSocketAddress
) : Parcelable {

    fun tryOpenInPort() =
        try {
            Result.success(OSCPortIn(local))
        } catch (e: Exception) {
            val error = OSCPortOpeningException(
                OSCPort.In, "Could not open in port!", e
            )
            Result.failure(error)
        }

    fun tryOpenOutPort() =
        try {
            Result.success(OSCPortOut(remote))
        } catch (e: Exception) {
            val error = OSCPortOpeningException(
                OSCPort.Out, "Could not open out port!", e
            )
            Result.failure(error)
        }

}