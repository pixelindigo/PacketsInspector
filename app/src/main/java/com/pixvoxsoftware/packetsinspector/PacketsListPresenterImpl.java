package com.pixvoxsoftware.packetsinspector;

/**
 * Created by PixelIndigo.
 */
public class PacketsListPresenterImpl implements PacketsListPresenter, PacketsProvider.OnPacketCapturedListener {

    private PacketsListView view;
    private PacketsProvider provider;

    public PacketsListPresenterImpl(final PacketsListView view) {
        this.provider = new PacketsProvider(this);
        this.view = view;
    }

    @Override
    public void loadPackets(String path) {
        provider.execute(path);
    }

    @Override
    public void onPacketCaptured(final Packet packet) {
        this.view.addPacket(packet);
    }
}
