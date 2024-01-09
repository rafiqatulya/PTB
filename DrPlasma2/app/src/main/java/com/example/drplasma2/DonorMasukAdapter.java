package com.example.drplasma2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DonorMasukAdapter extends RecyclerView.Adapter<DonorMasukAdapter.DonorMasukViewHolder> {

    private Context context;
    private List<DonorMasuk> donors;
    private OnItemClickListener onItemClickListener;

    public DonorMasukAdapter(Context context, List<DonorMasuk> donors) {
        this.context = context;
        this.donors = donors;
    }

    @NonNull
    @Override
    public DonorMasukViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_donor_masuk, parent, false);
        return new DonorMasukViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonorMasukViewHolder holder, int position) {
        DonorMasuk donor = donors.get(position);
        holder.namaPencari.setText(donor.getNamaPencari());
        holder.golonganDarah.setText(donor.getGolonganDarah());
        holder.nomorTelepon.setText(donor.getNomorTelepon());

        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(donor.getID_Permintaan());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return donors.size();
    }

    public interface OnItemClickListener {
        void onItemClick(String idPermintaan);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public static class DonorMasukViewHolder extends RecyclerView.ViewHolder {
        TextView namaPencari, golonganDarah, nomorTelepon;
        Button btnDetail;

        public DonorMasukViewHolder(@NonNull View itemView) {
            super(itemView);
            namaPencari = itemView.findViewById(R.id.namaPencari);
            golonganDarah = itemView.findViewById(R.id.golonganDarah);
            nomorTelepon = itemView.findViewById(R.id.nomorTelepon);
            btnDetail = itemView.findViewById(R.id.btn_detail_donor_masuk);
        }
    }
}
