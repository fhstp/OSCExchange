package ac.at.fhstp.digitech.oscexchange;

import java.net.InetSocketAddress;

public class OSCDevicePair {

    public final InetSocketAddress local;
    public final InetSocketAddress remote;


    public OSCDevicePair(InetSocketAddress local, InetSocketAddress remote) {
        this.local = local;
        this.remote = remote;
    }

}
