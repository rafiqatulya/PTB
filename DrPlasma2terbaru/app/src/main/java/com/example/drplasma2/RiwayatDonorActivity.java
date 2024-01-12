package com.example.drplasma2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RiwayatDonorActivity extends AppCompatActivity {

    private List<RiwayatDonor> riwayats = new ArrayList<>();
    private RiwayatDonorAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_donor);

        RecyclerView rvRiwayat = findViewById(R.id.rv_riwayat);
        rvRiwayat.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new RiwayatDonorAdapter(getApplicationContext(), riwayats);
        rvRiwayat.setAdapter(recyclerViewAdapter);

        //set referensi ke riwayat_permintaan
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("riwayat_permintaan");

        //mendengar satu kali permintaan perubahan
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                riwayats.clear(); //menghapus data lama dari list riwayats, dan memasukkan data baru dari Firebase.
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    RiwayatDonor riwayatDonor = snapshot.getValue(RiwayatDonor.class);
                    riwayats.add(riwayatDonor);
                }
                recyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            // Handle error if needed
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Button btnTambahRiwayat = findViewById(R.id.btn_tmbhriwayat);
        btnTambahRiwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toCari = new Intent(RiwayatDonorActivity.this, TambahRiwayatActivity.class);
                startActivity(toCari);
            }
        });

        Button btnDonorBerlangsung = findViewById(R.id.btn_donorberlangsung);
        btnDonorBerlangsung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toCari = new Intent(RiwayatDonorActivity.this, DonorActivity.class);
                startActivity(toCari);
            }
        });
    }
}

