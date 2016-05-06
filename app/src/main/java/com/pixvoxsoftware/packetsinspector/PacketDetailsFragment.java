package com.pixvoxsoftware.packetsinspector;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import org.jnetpcap.packet.PcapPacket;

public class PacketDetailsFragment extends Fragment {
    private static final String PARAM_PACKET = "packet";

    private AndroidTreeView treeView;
    private TreeNode root;

    private OnFragmentInteractionListener mListener;

    public PacketDetailsFragment() {
    }

    public static PacketDetailsFragment newInstance() {
        PacketDetailsFragment fragment = new PacketDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            root = TreeNode.root();
            this.treeView = new AndroidTreeView(getActivity(), root);
            this.treeView.setDefaultContainerStyle(R.style.DetailsItemNode);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_packet_details, container, false);

        container.addView(this.treeView.getView());
        this.treeView.expandAll();

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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
