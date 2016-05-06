package com.pixvoxsoftware.packetsinspector;

import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.network.Ip6;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.tcpip.Udp;

/**
 * Created by PixelIndigo.
 */
public class PacketInfo {

    private static Ip4 ip4;
    private static Ip6 ip6;
    private static Tcp tcp;
    private static Udp udp;

    static {
        ip4 = new Ip4();
        ip6 = new Ip6();
        tcp = new Tcp();
        udp = new Udp();
    }

    private String sourceIp, destinationIp;
    private int sourcePort, destinationPort;
    private int length;
    private long timestamp;

    public PacketInfo(PcapPacket pcapPacket) {
        if (pcapPacket.hasHeader(Ip4.ID)) {
            pcapPacket.getHeader(ip4);
            sourceIp = FormatUtils.ip(ip4.source());
            destinationIp = FormatUtils.ip(ip4.destination());
        } else if(pcapPacket.hasHeader(Ip6.ID)) {
            pcapPacket.getHeader(ip6);
            sourceIp = FormatUtils.
                    asStringIp6(ip6.source(), true).toLowerCase();
            destinationIp = FormatUtils.
                    asStringIp6(ip6.destination(), true).toLowerCase();
        }

        if (pcapPacket.hasHeader(Tcp.ID)) {
            pcapPacket.getHeader(tcp);
            sourcePort = tcp.source();
            destinationPort = tcp.destination();
        } else if (pcapPacket.hasHeader(Udp.ID)) {
            pcapPacket.getHeader(udp);
            sourcePort = udp.source();
            destinationPort = udp.destination();
        }


        timestamp = pcapPacket.getCaptureHeader().timestampInNanos();
        length = pcapPacket.getCaptureHeader().caplen();
    }

    public int getSourcePort() {
        return sourcePort;
    }

    public int getDestinationPort() {
        return destinationPort;
    }

    public int getLength() {
        return length;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public double getTimeShift(final PacketInfo prevPacket) {
        return (this.getTimestamp() - prevPacket.getTimestamp()) / 10e8;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public String getDestinationIp() {
        return destinationIp;
    }
}
