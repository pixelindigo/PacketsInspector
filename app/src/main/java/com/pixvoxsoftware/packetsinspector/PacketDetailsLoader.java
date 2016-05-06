package com.pixvoxsoftware.packetsinspector;

import android.content.AsyncTaskLoader;
import android.content.Context;

/**
 * Created by PixelIndigo.
 */
public class PacketDetailsLoader extends AsyncTaskLoader<PacketDetails> {
    public PacketDetailsLoader(Context context) {
        super(context);
    }

    @Override
    public PacketDetails loadInBackground() {
        return null;
    }
}
