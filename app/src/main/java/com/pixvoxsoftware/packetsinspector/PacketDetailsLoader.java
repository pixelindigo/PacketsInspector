package com.pixvoxsoftware.packetsinspector;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import org.jnetpcap.JBufferHandler;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapHeader;
import org.jnetpcap.nio.JBuffer;
import org.jnetpcap.nio.JMemory;
import org.jnetpcap.packet.PcapPacket;

/**
 * Created by PixelIndigo.
 */
public class PacketDetailsLoader extends AsyncTaskLoader<PacketDetails> {

    private StringBuilder errorBuffer;
    private String path;
    private long packetNumber;
    private PacketDetails packetDetails;

    public PacketDetailsLoader(Context context, String path, long packetNumber) {
        super(context);
        this.path = path;
        this.packetNumber = packetNumber;
        errorBuffer = new StringBuilder();
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (packetDetails != null) {
            deliverResult(packetDetails);
        }
        if (packetDetails == null) {
            forceLoad();
        }
    }

    @Override
    public PacketDetails loadInBackground() {
        Pcap pcap = Pcap.openOffline(path, errorBuffer);
        JBufferHandler<Integer> jBufferHandler = new JBufferHandler<Integer>() {
            @Override
            public void nextPacket(PcapHeader header, JBuffer buffer, Integer user) {
                // Pass
            }
        };
        //FIXME hack
        if (packetNumber > 1) {
            pcap.loop((int) (packetNumber - 1), jBufferHandler, 0);
        }

        PcapPacket pcapPacket = new PcapPacket(JMemory.POINTER);
        pcap.nextEx(pcapPacket);
        packetDetails = new PacketDetails(pcapPacket);
        pcap.close();
        return packetDetails;
    }


}
