package com.example.drplasma2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DonorMasukAdapter extends RecyclerView.Adapter<DonorMasukAdapter.DonorMasukViewHolder> {

    private Context context; //Menyimpan referensi ke konteks/objek aplikasi (database)
    private List<DonorMasuk> donors; //Menyimpan daftar objek DonorMasuk yang akan ditampilkan dalam RecyclerView.
    private OnItemClickListener onItemClickListener;

    // Konstruktor untuk adapter
    public DonorMasukAdapter(Context context, List<DonorMasuk> donors) {
        this.context = context;
        this.donors = donors;
    }

    // Membuat ViewHolder baru
    @NonNull
    @Override
    public DonorMasukViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Menginflate layout item_donor_masuk untuk setiap item dalam RecyclerView
        //inflate:  untuk mengonversi file layout XML ke tampilan yang dapat ditampilkan.
        View view = LayoutInflater.from(context).inflate(R.layout.item_donor_masuk, parent, false);
        return new DonorMasukViewHolder(view);
    }

    // Menghubungkan data ke ViewHolder
    @Override
    public void onBindViewHolder(@NonNull DonorMasukViewHolder holder, int position) {
        DonorMasuk donor = donors.get(position);

        // Menetapkan data ke TextView dalam ViewHolder
        //metode ini bertanggung jawab untuk menetapkan nilai-nilai data ke dalam tampilan setiap item di RecyclerView.
        holder.idPermintaan.setText(donor.getID_Permintaan());
        holder.namaPencari.setText("Nama Pencari: " + donor.getNamaPencari());
        holder.golonganDarah.setText("Golongan Darah: " + donor.getGolonganDarah());
        holder.nomorTelepon.setText("Nomor Telepon: " + donor.getNomorTelepon());

        // Menangani klik pada item RecyclerView
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    int adapterPosition = holder.getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        String idPermintaan = donors.get(adapterPosition).getID_Permintaan();
                        //menampilkn logcat (D)
                        Log.d("DonorMasukAdapter", "ID_Permintaan before click: " + idPermintaan);

                        // Memanggil metode onItemClick pada listener jika ID_Permintaan tidak kosong
                        if (idPermintaan != null && !idPermintaan.isEmpty()) {
                            onItemClickListener.onItemClick(idPermintaan);
                        } else {
                            // Menampilkan pesan kesalahan jika ID_Permintaan kosong
                            Log.e("DonorMasukAdapter", "ID_Permintaan is null or empty for this donor");
                            Toast.makeText(context, "ID_Permintaan is still loading. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    // Mendapatkan jumlah item dalam RecyclerView
    @Override
    public int getItemCount() {
        return donors.size();
    }

    // Interface untuk menangani klik pada item RecyclerView
    public interface OnItemClickListener {
        void onItemClick(String idPermintaan);
    }

    // Metode untuk mengatur listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    // ViewHolder untuk menyimpan referensi ke elemen tampilan
    public static class DonorMasukViewHolder extends RecyclerView.ViewHolder {
        TextView namaPencari, golonganDarah, nomorTelepon, idPermintaan;

        // Konstruktor ViewHolder
        public DonorMasukViewHolder(@NonNull View itemView) {
            super(itemView);
            // Mendapatkan referensi ke TextView dalam item_donor_masuk
            namaPencari = itemView.findViewById(R.id.namaPencari);
            golonganDarah = itemView.findViewById(R.id.golonganDarah);
            nomorTelepon = itemView.findViewById(R.id.nomorTelepon);
            idPermintaan = itemView.findViewById(R.id.idPermintaan);
        }
    }
}
