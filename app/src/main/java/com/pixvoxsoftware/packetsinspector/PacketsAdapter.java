package com.pixvoxsoftware.packetsinspector;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by PixelIndigo.
 */
public class PacketsAdapter extends RecyclerView.Adapter<PacketsAdapter.ViewHolder> {

    private ArrayList<Packet> packets;
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
        this.packets = new ArrayList<>();
    }

    @Override
    public PacketsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.packet_view, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Packet packet = packets.get(position);

        holder.number.setText(String.valueOf(position + 1));
        holder.scr_ip.setText(packet.getSourceIp());
        holder.dest_ip.setText(packet.getDestinationIp());
        holder.length.setText(String.valueOf(packet.getLength()).concat(" bytes"));
        holder.time.setText(String.format("%.6f", packet.getTimeShift(packets.get(0))));

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPacketSelected(packet);
            }
        });
    }

    @Override
    public int getItemCount() {
        return packets.size();
    }

    public void addPacket(final Packet packet) {
        this.packets.add(packet);
        this.notifyDataSetChanged();
    }

    interface OnPacketSelectedListener{
        void onPacketSelected(final Packet packet);
    }
}
