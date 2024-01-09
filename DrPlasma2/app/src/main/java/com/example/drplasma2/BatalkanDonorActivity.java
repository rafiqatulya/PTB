package com.example.drplasma2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BatalkanDonorActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batalkan_donor);

        databaseReference = FirebaseDatabase.getInstance().getReference("permintaan_donor");

        databaseReference.orderByChild("timestamp").limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PermintaanDonor permintaanDonor = snapshot.getValue(PermintaanDonor.class);
                    if (permintaanDonor != null) {
                        updateUI(permintaanDonor);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Button bataldaftarButton = findViewById(R.id.bataldaftar);
        bataldaftarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showConfirmationDialogBatalDonor();
            }
        });

    }

    private void updateUI(PermintaanDonor permintaanDonor) {
        // Update UI dengan data terbaru
        TextView textViewNamaPencari = findViewById(R.id.namaPencari);
        TextView textViewGolonganDarah = findViewById(R.id.golonganDarah);
        TextView textViewJumlahKebutuhanDarah = findViewById(R.id.jumlahKebutuhanDarah);
        TextView textViewNomorTelepon = findViewById(R.id.nomorTelepon);
        TextView textViewRumahSakit = findViewById(R.id.rumahSakit);
        TextView textViewLokasiRumahSakit = findViewById(R.id.LokRumahSakit);

        textViewNamaPencari.setText(permintaanDonor.getNamaPencari());
        textViewGolonganDarah.setText(permintaanDonor.getGolonganDarah());
        textViewJumlahKebutuhanDarah.setText(permintaanDonor.getJumlahKebutuhanDarah());
        textViewNomorTelepon.setText(permintaanDonor.getNomorTelepon());
        textViewRumahSakit.setText(permintaanDonor.getRumahSakit());
        textViewLokasiRumahSakit.setText(permintaanDonor.getLokasiRumahSakit());
    }

    private void showConfirmationDialogBatalDonor() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View customDialogView = getLayoutInflater().inflate(R.layout.custom_dialog3, null);
        builder.setView(customDialogView);

        AlertDialog dialog = builder.create();

        Button batlBatalButton = customDialogView.findViewById(R.id.btnBatal3);
        Button konfirmasiButton = customDialogView.findViewById(R.id.btnKonfirm3);

        konfirmasiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BatalkanDonorActivity.this, RiwayatDonorActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        batlBatalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}
