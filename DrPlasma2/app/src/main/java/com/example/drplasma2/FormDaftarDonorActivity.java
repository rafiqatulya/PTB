package com.example.drplasma2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FormDaftarDonorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_daftar_donor);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            String namaPencari = extras.getString("namaPencari");

            EditText editTextNamaPencari = findViewById(R.id.editTextNamaPencari);
            editTextNamaPencari.setText(namaPencari);
        }

        Button alertButton = findViewById(R.id.alertbtndaftar);
        alertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog();
            }
        });
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View customDialogView = getLayoutInflater().inflate(R.layout.custom_dialog, null);
        builder.setView(customDialogView);

        AlertDialog dialog = builder.create();
        Button konfirmButton = customDialogView.findViewById(R.id.konfirm);
        Button batlButton = customDialogView.findViewById(R.id.batlbtn);

        konfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDonorData();


                Intent intent = new Intent(FormDaftarDonorActivity.this, DonorActivity.class);
                startActivity(intent);

                dialog.dismiss();
            }
        });

        batlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void saveDonorData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference donorsRef = database.getReference("donors");

        EditText editTextNamaPendonor = findViewById(R.id.editTextNamaPendonor);
        EditText editTextGolonganDarah = findViewById(R.id.editTextGolonganDarah);
        EditText editTextNoTelepon = findViewById(R.id.editTextNoTelepon);
        EditText editTextNamaPencari = findViewById(R.id.editTextNamaPencari);

        Donor donor = new Donor(
                editTextNamaPendonor.getText().toString(),
                editTextGolonganDarah.getText().toString(),
                editTextNoTelepon.getText().toString(),
                editTextNamaPencari.getText().toString()
        );

        donorsRef.push().setValue(donor);

        Toast.makeText(FormDaftarDonorActivity.this, "Data berhasil terkirim", Toast.LENGTH_SHORT).show();
    }
}
