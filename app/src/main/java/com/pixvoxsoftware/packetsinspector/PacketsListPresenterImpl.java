package com.pixvoxsoftware.packetsinspector;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;

import java.util.List;

/**
 * Created by PixelIndigo.
 */
public class PacketsListPresenterImpl implements PacketsListPresenter{

    private PacketsListView view;
    public PacketsListPresenterImpl(final PacketsListView view) {
        this.view = view;
    }

    @Override
    public android.support.v4.content.Loader<List<PacketInfo>> onCreateLoader(int id, Bundle args) {
        return new PacketsLoader(view.getContext(), args.getString("filepath"));
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<List<PacketInfo>> loader, List<PacketInfo> data) {
        view.setPackets(data);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<List<PacketInfo>> loader) {

    }

}
