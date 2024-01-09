package com.example.drplasma2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RiwayatPermintaanAdapter extends RecyclerView.Adapter<RiwayatPermintaanAdapter.ViewHolder> {

    private Context context;
    private List<RiwayatPermintaan> riwayatsPermintaan;

    public RiwayatPermintaanAdapter(Context context, List<RiwayatPermintaan> riwayatsPermintaan) {
        this.context = context;
        this.riwayatsPermintaan = riwayatsPermintaan;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_riwayat_permintaan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RiwayatPermintaan riwayatPermintaan = riwayatsPermintaan.get(position);

        holder.statusTextView.setText(riwayatPermintaan.getStatus());
        holder.namaTextView.setText(riwayatPermintaan.getName());
        holder.goldarTextView.setText(riwayatPermintaan.getGoldar());
        holder.telpTextView.setText(riwayatPermintaan.getNotelp());
        holder.imgStatus.setImageResource(riwayatPermintaan.getImage());
    }

    @Override
    public int getItemCount() {
        return riwayatsPermintaan.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView statusTextView, namaTextView, goldarTextView, telpTextView;
        ImageView imgStatus;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            statusTextView = itemView.findViewById(R.id.status_riwayatPermintaan);
            namaTextView = itemView.findViewById(R.id.nama_riwayatPermintaan);
            goldarTextView = itemView.findViewById(R.id.goldar_riwayatPermintaan);
            telpTextView = itemView.findViewById(R.id.telp_riwayatPermintaan);
            imgStatus = itemView.findViewById(R.id.imgv_statusPermintaan);
            cardView = itemView.findViewById(R.id.card_riwayatPermintaan);
        }
    }
}
