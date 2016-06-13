package com.pixvoxsoftware.packetsinspector;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements PacketsListFragment.OnShowPacketListener, PacketDetailsFragment.OnFragmentInteractionListener,
        FragmentManager.OnBackStackChangedListener{

    public final static String SELECTED_FILE = "selected_file";

    @Bind(R.id.activity_main_toolbar)
    Toolbar toolbar;

    private PacketsListFragment packetsListFragment;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            if (getIntent().getAction() != null &&
                    getIntent().getAction().equals(Intent.ACTION_VIEW)) {
                path = getIntent().getData().getPath();
            } else {
                path = getIntent().getStringExtra(SELECTED_FILE);
            }
            packetsListFragment = PacketsListFragment.newInstance(path);

            getSupportFragmentManager().beginTransaction().add(R.id.main_fragment, packetsListFragment)
                    .commit();

            if (getSupportActionBar() != null) {
                File file = new File(path);
                getSupportActionBar().setTitle(file.getName());
            }
        }

        getSupportFragmentManager().addOnBackStackChangedListener(this);

        configureUpButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_packets_list, menu);

        MenuItem filterItem = menu.findItem(R.id.action_filter);
        if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
            filterItem.setVisible(false);
        } else {
            SearchView searchView =
                    (SearchView) MenuItemCompat.getActionView(filterItem);

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    packetsListFragment.filterPackets(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    packetsListFragment.filterPackets(newText);
                    return false;
                }
            });

        }
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

    private void configureUpButton() {
        if (getSupportActionBar() != null) {
            boolean enabled = getSupportFragmentManager().getBackStackEntryCount() != 0;
            getSupportActionBar().setDisplayHomeAsUpEnabled(enabled);
            invalidateOptionsMenu();
        }
    }

    @Override
    public void onBackStackChanged() {
        configureUpButton();
    }

    @Override
    public boolean onSupportNavigateUp() {
        getSupportFragmentManager().popBackStack();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
