package com.example.drplasma2;

import android.content.Intent;
import android.os.Bundle;
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

    private TextView textViewNamaPencari, textViewGolonganDarah, textViewJumlahKebutuhanDarah,
            textViewNomorTelepon, textViewRumahSakit, textViewLokasiRumahSakit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_donor_masuk);

        textViewNamaPencari = findViewById(R.id.editTextNamaPencari);
        textViewGolonganDarah = findViewById(R.id.editTextGolonganDarah);
        textViewJumlahKebutuhanDarah = findViewById(R.id.editTextJumlahKebutuhanDarah);
        textViewNomorTelepon = findViewById(R.id.editTextNomorTelepon);
        textViewRumahSakit = findViewById(R.id.editTextRumahSakit);
        textViewLokasiRumahSakit = findViewById(R.id.editTextLokasiRumahSakit);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String namaPencari = extras.getString("namaPencari");
            String golonganDarah = extras.getString("golonganDarah");
            String jumlahKebutuhanDarah = extras.getString("jumlahKebutuhanDarah");
            String nomorTelepon = extras.getString("nomorTelepon");
            String rumahSakit = extras.getString("rumahSakit");
            String lokasiRumahSakit = extras.getString("lokasiRumahSakit");

            textViewNamaPencari.setText(namaPencari);
            textViewGolonganDarah.setText(golonganDarah);
            textViewJumlahKebutuhanDarah.setText(jumlahKebutuhanDarah);
            textViewNomorTelepon.setText(nomorTelepon);
            textViewRumahSakit.setText(rumahSakit);
            textViewLokasiRumahSakit.setText(lokasiRumahSakit);

            getDataFromFirebase();
        }

        Button daftarDonorButton = findViewById(R.id.daftardonor);
        daftarDonorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

    private void getDataFromFirebase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("permintaan_donor");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DonorMasuk donor = snapshot.getValue(DonorMasuk.class);

                    if (donor != null && donor.getNamaPencari().equals(textViewNamaPencari.getText().toString())) {

                        textViewGolonganDarah.setText(donor.getGolonganDarah());
                        textViewJumlahKebutuhanDarah.setText(donor.getJumlahKebutuhanDarah());
                        textViewNomorTelepon.setText(donor.getNomorTelepon());
                        textViewRumahSakit.setText(donor.getRumahSakit());
                        textViewLokasiRumahSakit.setText(donor.getLokasiRumahSakit());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
