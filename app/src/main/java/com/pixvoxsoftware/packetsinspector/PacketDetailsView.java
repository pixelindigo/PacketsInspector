package com.pixvoxsoftware.packetsinspector;

import android.content.Context;

/**
 * Created by PixelIndigo.
 */
public interface PacketDetailsView {
    Context getContext();
    void setData(PacketDetails packetDetails);
}
