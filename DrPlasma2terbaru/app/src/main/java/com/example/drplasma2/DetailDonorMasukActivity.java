package com.example.drplasma2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailDonorMasukActivity extends AppCompatActivity {

    // TextView untuk menampilkan informasi donor
    private TextView textViewNamaPencari, textViewGolonganDarah, textViewJumlahKebutuhanDarah,
            textViewNomorTelepon, textViewRumahSakit, textViewLokasiRumahSakit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_donor_masuk);

        // Inisialisasi TextView
        textViewNamaPencari = findViewById(R.id.editTextNamaPencari);
        textViewGolonganDarah = findViewById(R.id.editTextGolonganDarah);
        textViewJumlahKebutuhanDarah = findViewById(R.id.editTextJumlahKebutuhanDarah);
        textViewNomorTelepon = findViewById(R.id.editTextNomorTelepon);
        textViewRumahSakit = findViewById(R.id.editTextRumahSakit);
        textViewLokasiRumahSakit = findViewById(R.id.editTextLokasiRumahSakit);

        // Mendapatkan ID_Permintaan dari Intent
        String idPermintaan = getIntent().getStringExtra("id_permintaan");
        Log.d("DetailDonorMasukActivity", "idPermintaan: " + idPermintaan);

        // Memastikan ID_Permintaan tidak kosong sebelum mengambil data
        if (idPermintaan != null && !idPermintaan.isEmpty()) {
            // Mengambil data donor dari Firebase
            getDataFromFirebase(idPermintaan);
        } else {
            // Menampilkan pesan kesalahan jika ID_Permintaan kosong
            Log.e("DetailDonorMasukActivity", "Invalid idPermintaan");
        }

        // Menangani klik pada tombol "Daftar Donor"
        Button daftarDonorButton = findViewById(R.id.daftardonor);
        daftarDonorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Membuka FormDaftarDonorActivity dan mengirim data yang diambil dari Firebase
                Intent intent = new Intent(DetailDonorMasukActivity.this, FormDaftarDonorActivity.class);
                intent.putExtra("namaPencari", textViewNamaPencari.getText().toString());
                intent.putExtra("golonganDarah", textViewGolonganDarah.getText().toString());
                intent.putExtra("jumlahKebutuhanDarah", textViewJumlahKebutuhanDarah.getText().toString());
                intent.putExtra("nomorTelepon", textViewNomorTelepon.getText().toString());
                intent.putExtra("rumahSakit", textViewRumahSakit.getText().toString());
                intent.putExtra("lokasiRumahSakit", textViewLokasiRumahSakit.getText().toString());
                startActivity(intent);
            }
        });
    }

    // Method untuk mengambil data donor dari Firebase
    private void getDataFromFirebase(String idPermintaan) {
        Log.d("DetailDonorMasukActivity", "Received id_permintaan: " + idPermintaan);

        // Mendapatkan referensi database Firebase untuk permintaan donor tertentu (menentukan path)
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("permintaan_donor").child(idPermintaan);

        // Mendengarkan perubahan data pada Firebase Database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Mengonversi snapshot (data dari firebase) menjadi objek DonorMasuk
                    DonorMasuk donor = dataSnapshot.getValue(DonorMasuk.class);

                    if (donor != null) {
                        // Menampilkan data donor yang berhasil diambil dari Firebase
                        Log.d("DetailDonorMasukActivity", "Data retrieved successfully");
                        Log.d("DetailDonorMasukActivity", "NamaPencari: " + donor.getNamaPencari());
                        Log.d("DetailDonorMasukActivity", "GolonganDarah: " + donor.getGolonganDarah());
                        Log.d("DetailDonorMasukActivity", "JumlahKebutuhanDarah: " + donor.getJumlahKebutuhanDarah());
                        Log.d("DetailDonorMasukActivity", "NomorTelepon: " + donor.getNomorTelepon());
                        Log.d("DetailDonorMasukActivity", "RumahSakit: " + donor.getRumahSakit());
                        Log.d("DetailDonorMasukActivity", "LokasiRumahSakit: " + donor.getLokasiRumahSakit());

                        // Menampilkan data donor ke dalam TextView
                        textViewNamaPencari.setText(donor.getNamaPencari());
                        textViewGolonganDarah.setText(donor.getGolonganDarah());
                        textViewJumlahKebutuhanDarah.setText(donor.getJumlahKebutuhanDarah());
                        textViewNomorTelepon.setText(donor.getNomorTelepon());
                        textViewRumahSakit.setText(donor.getRumahSakit());
                        textViewLokasiRumahSakit.setText(donor.getLokasiRumahSakit());
                    } else {
                        // Menampilkan pesan kesalahan jika objek donor null
                        Log.e("DetailDonorMasukActivity", "Donor object is null");
                    }
                } else {
                    // Menampilkan pesan kesalahan jika data tidak ditemukan
                    Log.e("DetailDonorMasukActivity", "No data found for ID_Permintaan: " + idPermintaan);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Menangani kesalahan saat mengambil data dari Firebase
                Log.e("DetailDonorMasukActivity", "Error getting data from Firebase: " + databaseError.getMessage());
            }
        });
        Log.d("DetailDonorMasukActivity", "Received id_permintaan: " + idPermintaan);
    }
}
