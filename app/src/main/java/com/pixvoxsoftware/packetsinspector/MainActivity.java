package com.pixvoxsoftware.packetsinspector;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.jnetpcap.Pcap;
import org.jnetpcap.packet.JPacket;
import org.jnetpcap.packet.JPacketHandler;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.protocol.network.Ip4;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.packets_view) RecyclerView packetsView;
    RecyclerView.Adapter packetsAdapter;
    RecyclerView.LayoutManager packetsLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        packetsView.setHasFixedSize(true);

        packetsLayoutManager = new LinearLayoutManager(this);
        packetsView.setLayoutManager(packetsLayoutManager);

        final ArrayList<JPacket> packets = new ArrayList<>();

        StringBuilder errBuf = new StringBuilder();
        Pcap pcap = Pcap.openOffline(Environment.getExternalStorageDirectory().getPath()+"/newtoy.pcap", errBuf);

        if(pcap != null) {
            JPacketHandler<String> jpacketHandler = new JPacketHandler<String>() {

                @Override
                public void nextPacket(JPacket packet, String user) {
                    packets.add(packet);
                }
            };

            pcap.loop(100, jpacketHandler, null);  // Just the first 100 packets for testing
            pcap.close();


            packetsAdapter = new PacketsAdapter(packets);
            packetsView.setAdapter(packetsAdapter);

        }

    }
}
