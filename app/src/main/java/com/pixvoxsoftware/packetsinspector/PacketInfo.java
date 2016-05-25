package com.pixvoxsoftware.packetsinspector;

import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.annotate.Format;
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.network.Ip6;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.tcpip.Udp;

import java.util.Locale;

/**
 * Created by PixelIndigo.
 */
public class PacketInfo {

    enum Protocol {UDP, TCP};

    public static final String UDP = "UDP";
    public static final String TCP = "TCP";

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
    private String sourceAddress, destinationAddress;
    private int sourcePort, destinationPort;
    private int length;
    private long timestamp;
    private final long frameNumber;
    private Protocol protocol;

    public PacketInfo(PcapPacket pcapPacket) {

        frameNumber = pcapPacket.getFrameNumber();

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
            protocol = Protocol.TCP;
        } else if (pcapPacket.hasHeader(Udp.ID)) {
            pcapPacket.getHeader(udp);
            sourcePort = udp.source();
            destinationPort = udp.destination();
            protocol = Protocol.UDP;
        }

        sourceAddress = String.format(Locale.ENGLISH, "%s:%d", sourceIp, sourcePort);
        destinationAddress = String.format(Locale.ENGLISH, "%s:%d", destinationIp, destinationPort);


        timestamp = pcapPacket.getCaptureHeader().timestampInNanos();
        length = pcapPacket.getCaptureHeader().caplen();
    }

    public long getFrameNumber() {
        return frameNumber;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public int getSourcePort() {
        return sourcePort;
    }

    public int getDestinationPort() {
        return destinationPort;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
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
