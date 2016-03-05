package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pixvoxsoftware.packetsinspector.PacketsAdapter;
import com.pixvoxsoftware.packetsinspector.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PacketsListFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    private PacketsAdapter packetsAdapter;

    @Bind(R.id.packets_view)
    RecyclerView packetsView;

    public PacketsListFragment() {
    }

    public static PacketsListFragment newInstance(PacketsAdapter packetsAdapter) {
        PacketsListFragment fragment = new PacketsListFragment();
        fragment.setPacketsAdapter(packetsAdapter);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    public void setPacketsAdapter(PacketsAdapter packetsAdapter) {
        this.packetsAdapter = packetsAdapter;
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
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
