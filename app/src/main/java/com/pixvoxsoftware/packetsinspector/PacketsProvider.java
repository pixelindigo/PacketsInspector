package com.pixvoxsoftware.packetsinspector;

import android.os.AsyncTask;

import org.jnetpcap.Pcap;
import org.jnetpcap.packet.JPacket;
import org.jnetpcap.packet.JPacketHandler;

/**
 * Created by PixelIndigo.
 */
public class PacketsProvider extends AsyncTask<String, Packet, Void>{

    private OnPacketCapturedListener listener;
    private StringBuilder errorBuffer;

    public PacketsProvider (final OnPacketCapturedListener listener) {
        this.listener = listener;
        this.errorBuffer = new StringBuilder();
    }

    @Override
    protected Void doInBackground(String... params) {
        Pcap pcap = Pcap.openOffline(params[0], this.errorBuffer);
        if(pcap != null) {
            JPacketHandler<String> jpacketHandler = new JPacketHandler<String>() {

                @Override
                public void nextPacket(JPacket packet, String user) {
                    publishProgress(new Packet(packet));
                }
            };

            pcap.loop(Pcap.LOOP_INFINITE, jpacketHandler, null);
            pcap.close();

        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Packet... values) {
        super.onProgressUpdate(values);
        listener.onPacketCaptured(values[0]);
    }

    interface OnPacketCapturedListener{
        void onPacketCaptured(final Packet packet);
    }

}