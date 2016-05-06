package com.pixvoxsoftware.packetsinspector;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PacketsListFragment extends Fragment implements PacketsListView, PacketsAdapter.OnPacketSelectedListener {
    private static final String PARAM_PATH = "filepath";

    private OnShowPacketListener onShowPacketListener;

    private PacketsAdapter packetsAdapter;
    private PacketsListPresenter presenter;

    @Bind(R.id.packets_view)
    RecyclerView packetsView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    public PacketsListFragment() {
        this.packetsAdapter = new PacketsAdapter(this);
        this.presenter = new PacketsListPresenterImpl(this);
    }

    public static PacketsListFragment newInstance(String filepath) {
        PacketsListFragment fragment = new PacketsListFragment();
        Bundle args = new Bundle();
        args.putString(PARAM_PATH, filepath);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            getLoaderManager().initLoader(0, getArguments(), presenter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_packets, container, false);
        ButterKnife.bind(this, view);

        this.packetsView.setHasFixedSize(true);

        packetsView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        this.packetsView.setAdapter(this.packetsAdapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnShowPacketListener) {
            this.onShowPacketListener = (OnShowPacketListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.onShowPacketListener = null;
    }

    @Override
    public void onPacketSelected(final PacketInfo packet) {
        this.onShowPacketListener.onShowPacket(packet);
    }

    @Override
    public void setPackets(List<PacketInfo> packets) {
        this.packetsAdapter.setData(packets);
        progressBar.setVisibility(View.INVISIBLE);
    }


    public interface OnShowPacketListener{
        void onShowPacket(final PacketInfo packet);
    }
}
