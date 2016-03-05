package com.pixvoxsoftware.packetsinspector;

import android.os.AsyncTask;

import org.jnetpcap.Pcap;
import org.jnetpcap.packet.JPacket;
import org.jnetpcap.packet.JPacketHandler;

import java.util.ArrayList;

/**
 * Created by m4s4n0bu on 03.03.16.
 */
public class PacketsLoader extends AsyncTask<Integer, Void, Void>{

    private ArrayList<JPacket> packets;
    private StringBuilder errorBuffer;
    private String path;

    public PacketsLoader(final String path, PacketsAdapter packetsAdapter) {
        this.packets = new ArrayList<>();
        packetsAdapter.setData(this.packets);

        this.errorBuffer = new StringBuilder();
        this.path = path;
    }

    @Override
    protected Void doInBackground(Integer... params) {

        Pcap pcap = Pcap.openOffline(path, this.errorBuffer);

        if(pcap != null) {
            JPacketHandler<String> jpacketHandler = new JPacketHandler<String>() {

                @Override
                public void nextPacket(JPacket packet, String user) {
                    packets.add(packet);
                }
            };

            pcap.loop(-1, jpacketHandler, null);
            pcap.close();

        }

        return null;
    }
}
