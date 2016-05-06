package com.pixvoxsoftware.packetsinspector;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import org.jnetpcap.Pcap;
import org.jnetpcap.nio.JMemory;
import org.jnetpcap.packet.PcapPacket;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PixelIndigo.
 */
public class PacketsLoader extends AsyncTaskLoader<List<PacketInfo>> {

    private final String path;
    private Pcap pcap;
    private StringBuilder errorBuffer;
    private ArrayList<PacketInfo> data;

    public PacketsLoader(Context context, String path) {
        super(context);
        this.path = path;
        errorBuffer = new StringBuilder();
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (data != null) {
            deliverResult(data);
        }
        if (data == null) {
            pcap = Pcap.openOffline(path, errorBuffer);
            forceLoad();
        }
    }

    @Override
    public List<PacketInfo> loadInBackground() {
        data = new ArrayList<>();
        PcapPacket pcapPacket = new PcapPacket(JMemory.POINTER);
        int i = 0;
        while(pcap.nextEx(pcapPacket) != Pcap.NEXT_EX_EOF) {
            data.add(new PacketInfo(pcapPacket));
            i++;
        }
        pcap.close();
        return data;
    }
}
