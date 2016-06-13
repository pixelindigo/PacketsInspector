package com.pixvoxsoftware.packetsinspector;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements PacketsListFragment.OnShowPacketListener, PacketDetailsFragment.OnFragmentInteractionListener {

    public final static String SELECTED_FILE = "selected_file";

    @Bind(R.id.activity_main_toolbar)
    Toolbar toolbar;

    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            path = getIntent().getStringExtra(SELECTED_FILE);
            PacketsListFragment packetsListFragment = PacketsListFragment.newInstance(path);

            getSupportFragmentManager().beginTransaction().add(R.id.main_fragment, packetsListFragment)
                    .commit();

            if (getSupportActionBar() != null) {
                File file = new File(path);
                getSupportActionBar().setTitle(file.getName());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_packets_list, menu);
        return true;
    }

    @Override
    public void onShowPacket(final PacketInfo packet) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment,
                PacketDetailsFragment.newInstance(path, packet.getFrameNumber())).addToBackStack(null).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
