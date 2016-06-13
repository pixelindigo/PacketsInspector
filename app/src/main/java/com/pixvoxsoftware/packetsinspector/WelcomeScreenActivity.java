package com.pixvoxsoftware.packetsinspector;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by PixelIndigo.
 */
public class WelcomeScreenActivity extends AppCompatActivity implements RecentFilesAdapter.OnRecentFileSelectedListener{

    private final static String FILES_HISTORY = "files_history";

    @Bind(R.id.recentFilesList)
    public RecyclerView mRecentFilesList;

    @Bind(R.id.recent_files_toolbar)
    public Toolbar mRecentFilesToolbar;

    RecentFilesAdapter mRecentFilesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_welcome_screen);

        ButterKnife.bind(this);

        setSupportActionBar(mRecentFilesToolbar);

        mRecentFilesAdapter = new RecentFilesAdapter(loadRecentFiles(), this);

        mRecentFilesList.setLayoutManager(new LinearLayoutManager(this));
        mRecentFilesList.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecentFilesList.setAdapter(mRecentFilesAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_welcome_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_browse_file:
                FileBrowserDialog fileBrowserDialog = new FileBrowserDialog();
                fileBrowserDialog.show(getSupportFragmentManager(), "dialog");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private ArrayList<String> loadRecentFiles() {
        SharedPreferences filesHistory = getSharedPreferences(FILES_HISTORY, MODE_PRIVATE);

//        editor.put
        String jsonFiles = filesHistory.getString(FILES_HISTORY, null);
        if (jsonFiles != null) {
            Gson gson = new Gson();
            ArrayList<String> files = (ArrayList<String>) gson.fromJson(jsonFiles, ArrayList.class);
            return files;
        }

        return new ArrayList<>();
    }


    private void updateRecentFiles(String path) {
        ArrayList<String> files = mRecentFilesAdapter.getFiles();
        if (files.contains(path)) {
            files.remove(path);
        }
        files.add(0, path);


        SharedPreferences filesHistory = getSharedPreferences(FILES_HISTORY, MODE_PRIVATE);
        SharedPreferences.Editor editor = filesHistory.edit();

        Gson gson = new Gson();
        String jsonFiles = gson.toJson(files);

        editor.putString(FILES_HISTORY, jsonFiles).apply();
    }

    public void onFileSelected(String path) {
        updateRecentFiles(path);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.SELECTED_FILE, path);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRecentFileSelected(String path) {
        onFileSelected(path);
    }
}
