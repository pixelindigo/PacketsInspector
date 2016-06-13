package com.pixvoxsoftware.packetsinspector;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by PixelIndigo.
 */
public class RecentFilesAdapter extends RecyclerView.Adapter<RecentFilesAdapter.ViewHolder> {

    private ArrayList<String> files;

    interface OnRecentFileSelectedListener {
        void onRecentFileSelected(String path);
    }

    private OnRecentFileSelectedListener onRecentFileSelectedListener;

    public RecentFilesAdapter(ArrayList<String> files, OnRecentFileSelectedListener onRecentFileSelectedListener) {
        this.files = files;
        this.onRecentFileSelectedListener = onRecentFileSelectedListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.file_label)
        public TextView label;

        public View view;
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            this.view = view;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_recent_file, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        File file = new File(files.get(position));
        holder.label.setText(file.getName());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecentFileSelectedListener.onRecentFileSelected(files.get(position));
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public ArrayList<String> getFiles() {
        return files;
    }
}
