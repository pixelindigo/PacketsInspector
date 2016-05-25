package com.pixvoxsoftware.packetsinspector;

import org.jnetpcap.PcapHeader;
import org.jnetpcap.nio.JMemory;
import org.jnetpcap.packet.JHeader;
import org.jnetpcap.packet.JHeaderPool;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.annotate.Header;
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.tcpip.Udp;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by PixelIndigo.
 */
public class PacketDetails {

    private static Tcp tcp;
    private static Udp udp;

    static {
        tcp = new Tcp();
        udp = new Udp();
    }

    private String protocol;
    private byte[] payload;
    private String payloadString;

    private List<HeaderDetails> headerDetailsList;

    class HeaderDetails {
        private String headerType;
        private byte[] payload;
        private String payloadString;

        public HeaderDetails(JHeader header) {
            headerType = header.getName();
            payload = header.getPayload();
            payloadString = PacketDetails.getHexDumpString(payload);
        }

        public String getHeaderType() {
            return headerType;
        }

        public String getPayloadString() {
            return payloadString;
        }
    }

    public PacketDetails(final PcapPacket pcapPacket) {
        headerDetailsList = new ArrayList<>();
        if (pcapPacket.hasHeader(Tcp.ID)) {
            pcapPacket.getHeader(tcp);
            protocol = "TCP";
            payload = tcp.getPayload();
        } else if (pcapPacket.hasHeader(Udp.ID)) {
            pcapPacket.getHeader(udp);
            protocol = "UDP";
            payload = udp.getPayload();
        }

        JHeaderPool jHeaderPool = new JHeaderPool();
        for (int i = 0; i < pcapPacket.getHeaderCount(); i++) {
            int id = pcapPacket.getHeaderIdByIndex(i);
            JHeader jHeader = jHeaderPool.getHeader(id);
            pcapPacket.getHeaderByIndex(i, jHeader);
            headerDetailsList.add(new HeaderDetails(jHeader));
        }


        payloadString = getHexDumpString(payload);
    }

    public static String getHexDumpString(byte[] hexdump) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < hexdump.length; i += 8) {
            stringBuilder.append(String.format(Locale.US, "%04x", i));
            for (int j = i; j < i + 8 && j < hexdump.length; j++) {
                stringBuilder.append(String.format(Locale.US, " %02X", hexdump[j]));
            }
            stringBuilder.append(" ");
            for (int j = i; j < i + 8 && j < hexdump.length; j++) {
                if (hexdump[j] >= 32 && hexdump[j] < 127) {
                    stringBuilder.append((char)hexdump[j]);
                } else {
                    stringBuilder.append('.');
                }
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public String getProtocol() {
        return protocol;
    }

    public byte[] getPayload() {
        return payload;
    }

    public String getPayloadString() {
        return payloadString;
    }

    public List<HeaderDetails> getHeaderDetailsList() {
        return headerDetailsList;
    }
}
