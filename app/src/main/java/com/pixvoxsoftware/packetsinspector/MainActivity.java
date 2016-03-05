package com.pixvoxsoftware.packetsinspector;

import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import layout.PacketsListFragment;

public class MainActivity extends AppCompatActivity implements PacketsListFragment.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PacketsAdapter packetsAdapter = new PacketsAdapter();
        PacketsLoader packetsLoader = new PacketsLoader(Environment.getExternalStorageDirectory().getPath()+"/newtoy.pcap", packetsAdapter);

        if (savedInstanceState == null) {
            PacketsListFragment packetsListFragment = PacketsListFragment.newInstance(packetsAdapter);

            getSupportFragmentManager().beginTransaction().add(R.id.main_fragment, packetsListFragment)
                    .commit();
        }
        packetsLoader.execute();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
