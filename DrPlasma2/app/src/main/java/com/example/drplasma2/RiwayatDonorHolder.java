package com.example.drplasma2;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RiwayatDonorHolder extends RecyclerView.ViewHolder {

    TextView statusView, nameView, goldarView, telpView;
    ImageView imgStatus;

    public RiwayatDonorHolder(@NonNull View itemView) {
        super(itemView);

        statusView = itemView.findViewById(R.id.status_riwayat);
        nameView = itemView.findViewById(R.id.nama_riwayat);
        goldarView = itemView.findViewById(R.id.goldar_riwayat);
        telpView = itemView.findViewById(R.id.telp_riwayat);
        imgStatus = itemView.findViewById(R.id.imgv_status);
    }
}
