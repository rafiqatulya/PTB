package com.example.drplasma2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PermintaanDonorActivity extends AppCompatActivity {

    //memanggil variabel untuk menyimpan data
    private TextView namaPencariTextView;
    private TextView golonganDarahTextView;
    private TextView jumlahKebutuhanDarahTextView;
    private TextView idPermintaanTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permintaan_donor);

        namaPencariTextView = findViewById(R.id.namaPencari);
        golonganDarahTextView = findViewById(R.id.golonganDarah);
        jumlahKebutuhanDarahTextView = findViewById(R.id.jumlahKebutuhanDarah);
        idPermintaanTextView = findViewById(R.id.idPermintaanTextView);

        Button btnLihatPermintaan = findViewById(R.id.btn_detail_permintaan_donor);
        btnLihatPermintaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idPermintaan = idPermintaanTextView.getText().toString().replace("ID Permintaan: ", "");

                Intent toDetail = new Intent(PermintaanDonorActivity.this, BatalkanPermintaanDonorActivity.class);
                toDetail.putExtra("ID_PERMINTAAN", idPermintaan);
                startActivity(toDetail);
            }
        });



        //ke RiwayatPermintaanActivity
        Button btnLihatRiwayatPermintaanDonor = findViewById(R.id.btnRiwayatPermintaanDonor);
        btnLihatRiwayatPermintaanDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toEvent = new Intent(PermintaanDonorActivity.this, RiwayatPermintaanActivity.class);
                startActivity(toEvent);
            }
        });

        //ke FormPermintaanActivity
        Button btFormPermintaan = findViewById(R.id.tambahpermintaan);
        btFormPermintaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toFormPermintaan = new Intent(PermintaanDonorActivity.this, FormPermintaanActivity.class);
                startActivity(toFormPermintaan);
            }
        });

        //Membuat referensi ke bagian "permintaan_donor" di Firebase Realtime Database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("permintaan_donor");

        //mengambil permintaan terakhir (memonitor perubahan pada anak-anak (child nodes) pada permintaan_donor)
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            // child baru ditambahkan ke dalam db
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                Log.d("PermintaanDonorActivity", "Child Added");
                updateUI(dataSnapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                Log.d("PermintaanDonorActivity", "Child Changed");
                updateUI(dataSnapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.d("PermintaanDonorActivity", "Child Removed");
            }

            @Override
            //Dipanggil ketika posisi (urutan) dari sebuah child berubah
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                Log.d("PermintaanDonorActivity", "Child Moved");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("PermintaanDonorActivity", "Database Error: " + databaseError.getMessage());
            }
        });
    }

    private void updateUI(DataSnapshot dataSnapshot) {
        //ambil id
        //memperbarui antarmuka pengguna (UI) dengan data yang diterima dari snapshot Firebase yang mewakili satu permintaan donor.
        String idPermintaan = dataSnapshot.getKey();

        Log.d("PermintaanDonorActivity", "ID Permintaan: " + idPermintaan);

        String namaPencari = dataSnapshot.child("namaPencari").getValue(String.class);
        String golonganDarah = dataSnapshot.child("golonganDarah").getValue(String.class);
        String jumlahKebutuhanDarah = dataSnapshot.child("jumlahKebutuhanDarah").getValue(String.class);

        TextView idPermintaanTextView = findViewById(R.id.idPermintaanTextView);
        idPermintaanTextView.setText("ID Permintaan: " + idPermintaan);

        namaPencariTextView.setText("Nama Pendonor: " + namaPencari);


        if (golonganDarah != null) {
            golonganDarahTextView.setText("Golongan Darah: " + golonganDarah);
//        } else {
//            golonganDarahTextView.setText("Golongan Darah: Tidak Tersedia");
        }

        if (jumlahKebutuhanDarah != null) {
            jumlahKebutuhanDarahTextView.setText("Kebutuhan Darah: " + jumlahKebutuhanDarah);
//        } else {
//            jumlahKebutuhanDarahTextView.setText("Kebutuhan Darah: Tidak Tersedia");
        }
    }
}
