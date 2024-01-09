package com.example.drplasma2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DonorActivity extends AppCompatActivity {

    private static final String TAG = "DonorActivity";
    private DatabaseReference donorsRef;

    TextView textViewNamaPendonor;
    TextView textViewNamaPenerima;
    TextView textViewGolonganDarah;

    ImageView imageView;
    Button DonorkanDariDonor;
    Button DonorKeRiwayat;
    View DonorBerlangsung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor);

        donorsRef = FirebaseDatabase.getInstance().getReference().child("donors");

        textViewNamaPendonor = findViewById(R.id.namapendonor);
        textViewNamaPenerima = findViewById(R.id.namapenerima);
        textViewGolonganDarah = findViewById(R.id.goldar);

        DonorBerlangsung = findViewById(R.id.cardView4);
        DonorKeRiwayat = findViewById(R.id.btn_riwayatdonor);
        DonorkanDariDonor = findViewById(R.id.donorkan);

        DonorkanDariDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DonorBerlangsung.setVisibility(View.GONE);
                DonorKeRiwayat.setVisibility(View.GONE);
                DonorkanDariDonor.setVisibility(View.GONE);
                Fragment fragment = new DonorMasukFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.donoractivity, fragment);
                fragmentTransaction.commit();
            }
        });

        Button btnLihat = findViewById(R.id.btn_detail_donor);
        btnLihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namaPendonor = textViewNamaPendonor.getText().toString();
                String namaPenerima = textViewNamaPenerima.getText().toString();
                String golonganDarah = textViewGolonganDarah.getText().toString();

                Intent toLihatDetailDonor = new Intent(DonorActivity.this, BatalkanDonorActivity.class);
                toLihatDetailDonor.putExtra("namaPendonor", namaPendonor);
                toLihatDetailDonor.putExtra("namaPenerima", namaPenerima);
                toLihatDetailDonor.putExtra("golonganDarah", golonganDarah);
                startActivity(toLihatDetailDonor);
            }
        });

        Button btnRiwayatDonor = findViewById(R.id.btn_riwayatdonor);
        btnRiwayatDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toRiwayatdonordaridonor = new Intent(DonorActivity.this, RiwayatDonorActivity.class);
                startActivity(toRiwayatdonordaridonor);
            }
        });

        getDonorData();
    }

    private void getDonorData() {
        donorsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot donorSnapshot : dataSnapshot.getChildren()) {
                        Donor donor = donorSnapshot.getValue(Donor.class);

                        if (donor != null) {
                            textViewNamaPendonor.setText("Nama Pendonor: " + donor.getNamaPendonor());
                            textViewNamaPenerima.setText("Nama Penerima: " + donor.getNamaPencari());
                            textViewGolonganDarah.setText("Golongan Darah: " + donor.getGolonganDarah());
                        }
                    }
                } else {
                    Log.d(TAG, "Data donor tidak ditemukan");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Gagal mengambil data donor: " + databaseError.getMessage());
            }
        });
    }
}
