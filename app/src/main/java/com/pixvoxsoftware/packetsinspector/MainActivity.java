package com.pixvoxsoftware.packetsinspector;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements PacketsListFragment.OnShowPacketListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            PacketsListFragment packetsListFragment = PacketsListFragment.newInstance(Environment.getExternalStorageDirectory().getPath()+"/newtoy.pcap");
            packetsListFragment.setRetainInstance(true);

            getSupportFragmentManager().beginTransaction().add(R.id.main_fragment, packetsListFragment)
                    .commit();
        }
    }

    @Override
    public void onShowPacket(final Packet packet) {

    }
}
