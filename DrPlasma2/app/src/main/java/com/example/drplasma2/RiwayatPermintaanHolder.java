package com.example.drplasma2;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drplasma2.R;

public class RiwayatPermintaanHolder extends RecyclerView.ViewHolder {
    TextView statusPermintaan, namePermintaan, goldarPermintaan, telpPermintaan;
    ImageView imgPermintaan;
    public RiwayatPermintaanHolder(@NonNull View itemView) {
        super(itemView);
        statusPermintaan = itemView.findViewById(R.id.status_riwayatPermintaan);
        namePermintaan = itemView.findViewById(R.id.nama_riwayatPermintaan);
        goldarPermintaan = itemView.findViewById(R.id.goldar_riwayatPermintaan);
        telpPermintaan = itemView.findViewById(R.id.telp_riwayatPermintaan);
        imgPermintaan = itemView.findViewById(R.id.imgv_statusPermintaan);
    }
}
