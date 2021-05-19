package ac.at.fhstp.digitech.oscexchange;

import java.net.InetSocketAddress;

/**
 * Contains information about a pair of devices that communicate over OSC
 */
public final class OSCDevicePair {

    /**
     * The address of this device
     */
    @PublicApi
    public final InetSocketAddress local;
    /**
     * The address of the other device
     */
    @PublicApi
    public final InetSocketAddress remote;


    @PublicApi
    public OSCDevicePair(InetSocketAddress local, InetSocketAddress remote) {
        this.local = local;
        this.remote = remote;
    }

}
