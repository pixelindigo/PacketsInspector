package com.pixvoxsoftware.packetsinspector;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by PixelIndigo.
 */
public class PacketDetailsAdapter extends RecyclerView.Adapter<PacketDetailsAdapter.ViewHolder> {

    private PacketDetails packetDetails = null;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.cardHeader)
        public TextView header;
        @Bind(R.id.cardPayload)
        public TextView payload;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PacketDetails.HeaderDetails headerDetails = packetDetails.getHeaderDetailsList().get(position);
        holder.header.setText(headerDetails.getHeaderType());
        holder.payload.setText(headerDetails.getPayloadString());

    }

    @Override
    public int getItemCount() {
        if (packetDetails == null) {
            return 0;
        }
        return packetDetails.getHeaderDetailsList().size();
    }

    public void setPacketDetails(PacketDetails packetDetails) {
        this.packetDetails = packetDetails;
        notifyDataSetChanged();
    }
}
