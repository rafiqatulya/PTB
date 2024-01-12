package com.example.drplasma2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class RiwayatPermintaanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_permintaan);

        List<RiwayatPermintaan> riwayatsPermintaan = new ArrayList<>();

        RecyclerView rvRiwayatPermintaan = findViewById(R.id.rv_riwayat_permintaan);
        rvRiwayatPermintaan.setLayoutManager(new LinearLayoutManager(this));
        RiwayatPermintaanAdapter riwayatAdapter = new RiwayatPermintaanAdapter(getApplicationContext(), riwayatsPermintaan);
        rvRiwayatPermintaan.setAdapter(riwayatAdapter);

        //referensi ke riwayat_permintaan
        DatabaseReference riwayatRef = FirebaseDatabase.getInstance().getReference("riwayat_permintaan");

        //mendengar satu kali permintaan perubahan
        riwayatRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                riwayatsPermintaan.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String status = snapshot.child("status").getValue(String.class);
                    String name = snapshot.child("name").getValue(String.class);
                    String notelp = snapshot.child("notelp").getValue(String.class);
                    String goldar = snapshot.child("goldar").getValue(String.class);

                    int imageDrawableId = R.drawable.gagal;

                    if ("Donor Selesai".equals(status)) {
                        imageDrawableId = R.drawable.success;
                    }
                    // menghapus data lama dari list riwayats, dan memasukkan data baru dari Firebase.
                    riwayatsPermintaan.add(new RiwayatPermintaan(status, name, notelp, goldar, imageDrawableId));
                }
                //memberitau data berubah
                riwayatAdapter.notifyDataSetChanged();
            }

            @Override
            // Handle error if needed
            public void onCancelled(DatabaseError databaseError) {
                // Handle onCancelled
            }
        });

        Button btnPermintaanDonorBerlangsung = findViewById(R.id.btn_permintaanberlangsung);
        btnPermintaanDonorBerlangsung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toCari = new Intent(RiwayatPermintaanActivity.this, PermintaanDonorActivity.class);
                startActivity(toCari);
            }
        });
    }
}
