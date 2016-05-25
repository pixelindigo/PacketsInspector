package com.pixvoxsoftware.packetsinspector;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import org.jnetpcap.packet.PcapPacket;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PacketDetailsFragment extends Fragment implements PacketDetailsView{
    public static final String PARAM_FILE = "filepath";
    public static final String PARAM_PACKET_NUMBER = "packetNumber";

    private String path;
    private long packetNumber;

    private PacketDetailsPresenter presenter;

    private OnFragmentInteractionListener mListener;

    private PacketDetailsAdapter packetDetailsAdapter;

    @Bind(R.id.cardsList)
    RecyclerView cardsList;

    public PacketDetailsFragment() {
        presenter = new PacketDetailsPresenterImpl(this);
        packetDetailsAdapter = new PacketDetailsAdapter();
    }

    public static PacketDetailsFragment newInstance(String path, long packetNumber) {
        PacketDetailsFragment fragment = new PacketDetailsFragment();
        Bundle args = new Bundle();
        args.putString(PARAM_FILE, path);
        args.putLong(PARAM_PACKET_NUMBER, packetNumber);
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
        View view = inflater.inflate(R.layout.fragment_packet_details, container, false);
        ButterKnife.bind(this, view);
        cardsList.setHasFixedSize(true);
        cardsList.setLayoutManager(new LinearLayoutManager(container.getContext()));
        cardsList.setAdapter(packetDetailsAdapter);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        //TODO Remove details
    }

    @Override
    public void setData(PacketDetails packetDetails) {
        packetDetailsAdapter.setPacketDetails(packetDetails);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
