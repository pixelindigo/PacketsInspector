package com.pixvoxsoftware.packetsinspector;

import org.jnetpcap.packet.JPacket;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.network.Ip6;
import org.jnetpcap.protocol.tcpip.Http;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.tcpip.Udp;

/**
 * Created by PixelIndigo.
 */
public class Packet {

    private final JPacket jPacket;

    //Protocols
    private static Ip4 ip4 = new Ip4();
    private static Ip6 ip6 = new Ip6();
    private static Udp udp = new Udp();
    private static Tcp tcp = new Tcp();
    private static Http http = new Http();

    private String sourceIp, destinationIp;

    public enum Protocol {UDP, TCP, HTTP, HTTPS, DNS}

    public Packet(final JPacket jPacket) {
        this.jPacket = jPacket;

        if (this.jPacket.hasHeader(Ip4.ID)) {
            this.jPacket.getHeader(ip4);
            this.sourceIp = org.jnetpcap.packet.format.FormatUtils.ip(ip4.source());
            this.destinationIp = org.jnetpcap.packet.format.FormatUtils.ip(ip4.destination());
        } else if(this.jPacket.hasHeader(Ip6.ID)) {
            this.jPacket.getHeader(ip6);
            this.sourceIp = org.jnetpcap.packet.format.FormatUtils.
                    asStringIp6(ip6.source(), true).toLowerCase();
            this.destinationIp = org.jnetpcap.packet.format.FormatUtils.
                    asStringIp6(ip6.destination(), true).toLowerCase();
        }

        if (this.jPacket.hasHeader(Tcp.ID)) {

        } else if (this.jPacket.hasHeader(Udp.ID)) {

        }
    }

    public long getTimestamp() {
        return this.jPacket.getCaptureHeader().timestampInNanos();
    }

    public double getTimeShift(final Packet prevPacket) {
        return (this.getTimestamp() - prevPacket.getTimestamp()) / 10e8;
    }

    public String getSourceIp() {
        return this.sourceIp;
    }

    public String getDestinationIp() {
        return this.destinationIp;
    }

    public int getLength() {
        return this.jPacket.getCaptureHeader().caplen();
    }
}
