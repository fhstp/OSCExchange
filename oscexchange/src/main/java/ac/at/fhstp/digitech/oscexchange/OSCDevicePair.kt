package ac.at.fhstp.digitech.oscexchange

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
)