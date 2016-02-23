package com.pixvoxsoftware.packetsinspector;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jnetpcap.packet.JPacket;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.network.Ip6;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by m4s4n0bu on 21.02.16.
 */
public class PacketsAdapter extends RecyclerView.Adapter<PacketsAdapter.ViewHolder> {

    private ArrayList<JPacket> packets;
    private Ip4 ip4;
    private Ip6 ip6;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        @Bind(R.id.number) public TextView number;
        @Bind(R.id.src_ip) public TextView scr_ip;
        @Bind(R.id.dest_ip) public TextView dest_ip;
        @Bind(R.id.protocol) public TextView protocol;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public PacketsAdapter(ArrayList<JPacket> packets) {
        this.packets = packets;
        this.ip4 = new Ip4();
        this.ip6 = new Ip6();
    }

    @Override
    public PacketsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.packet_view, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        JPacket packet = packets.get(position);

        byte[] dIp, sIp;
        String sourceIP = "", destinationIP = "";

        if (packet.hasHeader(Ip4.ID)) {
            dIp = packet.getHeader(ip4).destination();
            sIp = packet.getHeader(ip4).source();
            sourceIP = org.jnetpcap.packet.format.FormatUtils.ip(sIp);
            destinationIP = org.jnetpcap.packet.format.FormatUtils.ip(dIp);
        } else if (packet.hasHeader(Ip6.ID)) {
            dIp = packet.getHeader(ip6).destination();
            sIp = packet.getHeader(ip6).source();
            sourceIP = org.jnetpcap.packet.format.FormatUtils.asStringIp6(sIp, true);
            destinationIP = org.jnetpcap.packet.format.FormatUtils.asStringIp6(dIp, true);
        }

        holder.number.setText(String.valueOf(position));
        holder.scr_ip.setText(sourceIP);
        holder.dest_ip.setText(destinationIP);
    }

    @Override
    public int getItemCount() {
        return packets.size();
    }

}
