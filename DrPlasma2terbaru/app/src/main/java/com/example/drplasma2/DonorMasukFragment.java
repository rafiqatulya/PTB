package com.example.drplasma2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DonorMasukFragment extends Fragment {

    // List untuk menyimpan data donor
    private List<DonorMasuk> donors = new ArrayList<>();

    // Adapter untuk RecyclerView
    private DonorMasukAdapter recyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donor_masuk, container, false);

        // Mendapatkan referensi database Firebase (menentukan path yg dituju di database)
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("permintaan_donor");

        // Inisialisasi RecyclerView
        RecyclerView rvEvent = view.findViewById(R.id.rv_donor_masuk);
        rvEvent.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerViewAdapter = new DonorMasukAdapter(getContext(), donors);

        rvEvent.setAdapter(recyclerViewAdapter);

        // Menangani klik pada item RecyclerView
        recyclerViewAdapter.setOnItemClickListener(new DonorMasukAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String idPermintaan) {
                // Menangani klik pada item dengan membuka DetailDonorMasukActivity
                Log.d("DonorMasukAdapter", "Clicked ID_Permintaan: " + idPermintaan);
                if (idPermintaan != null && !idPermintaan.isEmpty()) {
                    Intent intent = new Intent(getContext(), DetailDonorMasukActivity.class);
                    //Menyematkan data tambahan keIntent
                    intent.putExtra("id_permintaan", idPermintaan);
                    startActivity(intent);
                } else {
                    // Menampilkan pesan kesalahan jika ID_Permintaan kosong
                    Log.e("DonorMasukAdapter", "ID_Permintaan is null or empty for this donor");
                    Toast.makeText(getContext(), "ID_Permintaan is still loading. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Mendengarkan perubahan data pada Firebase Database
        //Memproses perubahan data dari Firebase dan memperbarui adapter.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Memproses setiap snapshot dari database
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d("DonorMasukFragment", "Snapshot: " + snapshot.toString());

                    // Mengonversi snapshot menjadi objek DonorMasuk
                    DonorMasuk donor = snapshot.getValue(DonorMasuk.class);
                    if (donor != null) {
                        // Menetapkan ID_Permintaan dari key snapshot
                        donor.setID_Permintaan(snapshot.getKey());
                        Log.d("DonorMasukFragment", "Donor ID_Permintaan: " + donor.getID_Permintaan());
                        Log.d("DonorMasukFragment", "Donor details: " + donor.toString());

                        // Menambahkan donor ke daftar donor
                        donors.add(donor);
                    } else {
                        // Melewati donor yang bernilai null
                        Log.e("DonorMasukFragment", "Skipping null donor");
                    }
                }

                // Memberitahu adapter bahwa data telah berubah
                recyclerViewAdapter.notifyDataSetChanged();

                // Logging jumlah donor yang berhasil diambil dari Firebase
                Log.d("DonorMasukFragment", "Number of donors retrieved: " + donors.size());

                // Logging ID_Permintaan dari setiap donor
                for (DonorMasuk donor : donors) {
                    Log.d("DonorMasukFragment", "Donor ID_Permintaan: " + donor.getID_Permintaan());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Menangani kesalahan saat mengambil data dari Firebase
                Log.e("DonorMasukFragment", "Error fetching data from Firebase: " + databaseError.getMessage());
            }
        });

        // Logging jumlah donor setelah eksekusi database
        Log.d("DonorMasukFragment", "Number of donors retrieved: " + donors.size());

        return view;
    }
}
