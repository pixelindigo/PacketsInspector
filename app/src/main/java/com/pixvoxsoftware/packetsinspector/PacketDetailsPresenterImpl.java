package com.pixvoxsoftware.packetsinspector;

import android.support.v4.content.Loader;
import android.os.Bundle;

/**
 * Created by PixelIndigo.
 */
public class PacketDetailsPresenterImpl implements PacketDetailsPresenter {

    PacketDetailsView view;

    public PacketDetailsPresenterImpl(PacketDetailsView view) {
        this.view = view;
    }

    @Override
    public Loader<PacketDetails> onCreateLoader(int id, Bundle args) {
        return new PacketDetailsLoader(view.getContext(),
                args.getString(PacketDetailsFragment.PARAM_FILE),
                args.getLong(PacketDetailsFragment.PARAM_PACKET_NUMBER));
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<PacketDetails> loader, PacketDetails data) {

        view.setData(data);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<PacketDetails> loader) {

    }
}
