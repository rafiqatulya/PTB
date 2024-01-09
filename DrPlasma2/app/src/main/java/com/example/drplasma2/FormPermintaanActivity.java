package com.example.drplasma2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FormPermintaanActivity extends AppCompatActivity {
    private static final int MAP_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_permintaan);

        Button kirimPermintaanButton = findViewById(R.id.btnKirim);
        kirimPermintaanButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showConfirmationDialog();
            }
        });

        ImageView lokasicari = findViewById(R.id.lokasicari);
        lokasicari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialogForMaps();
            }
        });

    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View customDialogView = getLayoutInflater().inflate(R.layout.dialog_konfirmasi_caridonor, null);
        builder.setView(customDialogView);

        AlertDialog dialog = builder.create();

        Button batalButton = customDialogView.findViewById(R.id.batlkonfirm);
        Button konfirmasiButton = customDialogView.findViewById(R.id.konfirmcari);

        batalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        konfirmasiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequestToFirebase();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showCustomDialogForMaps() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View customDialogView = getLayoutInflater().inflate(R.layout.maps_dialog, null);
        builder.setView(customDialogView);

        AlertDialog dialog = builder.create();

        Button bukaMapButton = customDialogView.findViewById(R.id.bukamap);
        bukaMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(FormPermintaanActivity.this, MapsActivity.class);
                startActivityForResult(intent, MAP_REQUEST_CODE);
                dialog.dismiss();
            }
        });

        Button batlButton = customDialogView.findViewById(R.id.batlbtn);

        batlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MAP_REQUEST_CODE && resultCode == RESULT_OK) {
            String selectedLocation = data.getStringExtra("lokasi");
            if (selectedLocation != null) {
                EditText editTextLokasi = findViewById(R.id.editTextLokasiRumahSakit);
                editTextLokasi.setText(selectedLocation);
            }
        }
    }

    private void sendRequestToFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("permintaan_donor");

        String requestId = "ID_Permintaan_" + System.currentTimeMillis();
        DatabaseReference requestRef = myRef.child(requestId);

        String namaPencari = ((EditText) findViewById(R.id.editTextNamaPencari)).getText().toString();
        String golonganDarah = ((EditText) findViewById(R.id.editTextGolonganDarah)).getText().toString();
        String jumlahKebutuhanDarah = ((EditText) findViewById(R.id.editTextJumlahKebutuhanDarah)).getText().toString();
        String nomorTelepon = ((EditText) findViewById(R.id.editTextNomorTelepon)).getText().toString();
        String rumahSakit = ((EditText) findViewById(R.id.editTextRumahSakit)).getText().toString();
        String lokasiRumahSakit = ((EditText) findViewById(R.id.editTextLokasiRumahSakit)).getText().toString();

        requestRef.child("namaPencari").setValue(namaPencari);
        requestRef.child("golonganDarah").setValue(golonganDarah);
        requestRef.child("jumlahKebutuhanDarah").setValue(jumlahKebutuhanDarah);
        requestRef.child("nomorTelepon").setValue(nomorTelepon);
        requestRef.child("rumahSakit").setValue(rumahSakit);
        requestRef.child("lokasiRumahSakit").setValue(lokasiRumahSakit);
    }
}
