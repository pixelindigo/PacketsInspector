package com.pixvoxsoftware.packetsinspector;

import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by PixelIndigo.
 */
public class FileBrowserAdapter implements ListAdapter{

    private FileBrowser mFileBrowser;

    private ArrayList<DataSetObserver> observers = new ArrayList<DataSetObserver>();

    private int selected = -1;

    public FileBrowserAdapter(String directory) {
        mFileBrowser = new FileBrowser(directory);
    }

    public void notifyDataSetChanged(){
        for (DataSetObserver observer: observers) {
            observer.onChanged();
        }
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        observers.add(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        observers.remove(observer);
    }

    @Override
    public int getCount() {
        return mFileBrowser.getFiles().length;
    }

    @Override
    public File getItem(int position) {
        return mFileBrowser.getFiles()[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ItemViewHolder itemViewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_recent_file, parent, false);
            itemViewHolder = new ItemViewHolder(view);

            view.setTag(itemViewHolder);
        } else {
            itemViewHolder = (ItemViewHolder) view.getTag();
        }

        itemViewHolder.label.setText(getItem(position).getName());
        if (getItem(position).isDirectory()) {
            itemViewHolder.icon.setImageResource(R.drawable.ic_folder_black_24dp);
        } else {
            itemViewHolder.icon.setImageResource(R.drawable.ic_insert_drive_file_black_24dp);
        }

        if (position == selected) {
            itemViewHolder.view.setBackgroundResource(R.color.colorSelectedItem);
        } else {
            itemViewHolder.view.setBackgroundResource(android.R.color.background_light);
        }

        return view;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return mFileBrowser.getFiles().length == 0;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.file_label)
        public TextView label;
        @Bind(R.id.file_icon)
        public ImageView icon;

        public View view;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            this.view = view;
        }
    }

    public void goFolder(String folder) {
        mFileBrowser.nextDirectory(folder);
        selected = -1;
    }
    public File goUp() {
        selected = -1;
        return mFileBrowser.upDirectory();
    }

    public void setSelected(int position) {
        if (selected == position) {
            selected = -1;
        } else {
            selected = position;
        }
    }

    public int getSelected() {
        return selected;
    }
}
