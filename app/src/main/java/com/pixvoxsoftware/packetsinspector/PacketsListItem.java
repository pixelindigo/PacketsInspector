package com.pixvoxsoftware.packetsinspector;

import org.jnetpcap.packet.PcapPacket;

/**
 * Created by PixelIndigo.
 */
public class PacketsListItem {

    private final String srcIp, dstIp;
    private final int length;

    public PacketsListItem(PcapPacket pcapPacket) {
        srcIp = null;
        dstIp = null;
        length = 0;
    }

    public String getSrcIp() {
        return srcIp;
    }

    public String getDstIp() {
        return dstIp;
    }

    public int getLength() {
        return length;
    }
}
