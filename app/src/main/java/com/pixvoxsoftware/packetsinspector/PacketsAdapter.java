package com.pixvoxsoftware.packetsinspector;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by PixelIndigo.
 */
public class PacketsAdapter extends RecyclerView.Adapter<PacketsAdapter.ViewHolder> {

    private ArrayList<PacketInfo> allPackets;
    private ArrayList<PacketInfo> visiblePackets;
    
    private OnPacketSelectedListener listener;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.number) public TextView number;
        @Bind(R.id.src_ip) public TextView scr_ip;
        @Bind(R.id.dest_ip) public TextView dest_ip;
        @Bind(R.id.protocol) public TextView protocol;
        @Bind(R.id.time) public TextView time;
        @Bind(R.id.length) public TextView length;
        public View view;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            this.view = view;
        }
    }

    public PacketsAdapter(final OnPacketSelectedListener listener) {
        this.listener = listener;
        allPackets = new ArrayList<>();
        visiblePackets = new ArrayList<>();
    }

    @Override
    public PacketsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.packet_view, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final PacketInfo packet = visiblePackets.get(position);

        holder.number.setText(String.valueOf(packet.getFrameNumber()));
        holder.scr_ip.setText(packet.getSourceIp());
        holder.dest_ip.setText(packet.getDestinationIp());
        holder.length.setText(String.valueOf(packet.getLength()).concat(" bytes"));
        holder.time.setText(String.format(Locale.ENGLISH, "%.6f", packet.getTimeShift(allPackets.get(0))));

        switch (packet.getProtocol()) {
            case UDP:
                holder.protocol.setText(PacketInfo.UDP);
                break;
            case TCP:
                holder.protocol.setText(PacketInfo.TCP);
                break;
        }

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPacketSelected(packet);
            }
        });
    }

    @Override
    public int getItemCount() {
        return visiblePackets.size();
    }

    public void setData(final List<PacketInfo> allPackets) {
        this.allPackets.clear();
        this.allPackets.addAll(allPackets);
        visiblePackets.clear();
        visiblePackets.addAll(allPackets);
        notifyDataSetChanged();
    }

    public void filterData(String query) {
        visiblePackets.clear();
        if (isValidQuery(query)) {
            if (query.equals("")) {
                visiblePackets.addAll(allPackets);
            } else {
                for (PacketInfo packetInfo : allPackets) {
                    if (packetInfo.getSourceIp() != null && packetInfo.getSourceIp().equals(query) ||
                            packetInfo.getDestinationIp() != null && packetInfo.getDestinationIp().equals(query)) {
                        visiblePackets.add(packetInfo);
                    }
                }
            }
            notifyDataSetChanged();
        }
    }

    boolean isValidQuery(String query) {
        return query.equals("") || Utils.isValidAddress(query);
    }

    interface OnPacketSelectedListener{
        void onPacketSelected(final PacketInfo packet);
    }
}
