package com.pixvoxsoftware.packetsinspector;

import android.content.Context;

import java.util.List;

/**
 * Created by PixelIndigo.
 */
public interface PacketsListView {
    void setPackets(final List<PacketInfo> packets);
    void showLoadingDialog();
    void dismissLoadingDialog();
    Context getContext();
}
