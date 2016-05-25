package com.pixvoxsoftware.packetsinspector;

import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements PacketsListFragment.OnShowPacketListener, PacketDetailsFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            PacketsListFragment packetsListFragment = PacketsListFragment.newInstance(Environment.getExternalStorageDirectory().getPath()+"/newtoy.pcap");

            getSupportFragmentManager().beginTransaction().add(R.id.main_fragment, packetsListFragment)
                    .commit();
        }
    }

    @Override
    public void onShowPacket(final PacketInfo packet) {
        //TODO Fix
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment,
                PacketDetailsFragment.newInstance(Environment.getExternalStorageDirectory().getPath()+"/newtoy.pcap", packet.getFrameNumber())).addToBackStack(null).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
