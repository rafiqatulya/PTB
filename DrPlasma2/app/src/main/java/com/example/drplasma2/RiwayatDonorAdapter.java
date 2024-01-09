package com.example.drplasma2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RiwayatDonorAdapter extends RecyclerView.Adapter<RiwayatDonorHolder> {

    Context context;
    List<RiwayatDonor> items;

    public RiwayatDonorAdapter(Context context, List<RiwayatDonor> items) {
        this.context = context;
        this.items = items;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<RiwayatDonor> getItem() {
        return items;
    }

    public void setItem(List<RiwayatDonor> item) {
        this.items = item;
    }

    @NonNull
    @Override
    public RiwayatDonorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RiwayatDonorHolder(LayoutInflater.from(context).inflate(R.layout.item_row_riwayat_donor, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RiwayatDonorHolder holder, int position) {
        holder.statusView.setText(items.get(position).getStatus());
        holder.nameView.setText(items.get(position).getName());
        holder.goldarView.setText(items.get(position).getGoldar());
        holder.telpView.setText(items.get(position).getNotelp());
        holder.imgStatus.setImageResource(items.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
