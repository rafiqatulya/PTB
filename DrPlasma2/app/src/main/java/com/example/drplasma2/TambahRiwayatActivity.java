package com.example.drplasma2;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.drplasma2.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TambahRiwayatActivity extends AppCompatActivity {

    private EditText editTextNamaPencari;
    private EditText editTextTanggalDonor;
    private EditText editTextGolonganDarah;
    private EditText editTextJumlahKebutuhanDarah;
    private EditText editTextNomorTelepon;
    private EditText editTextRumahSakit;
    private EditText editTextLokasiRumahSakit;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_riwayat);

        databaseReference = FirebaseDatabase.getInstance().getReference("riwayat_permintaan");

        editTextNamaPencari = findViewById(R.id.editTextNamaPencari);
        editTextTanggalDonor = findViewById(R.id.editTextTanggalDonor);
        editTextGolonganDarah = findViewById(R.id.editTextGolonganDarah);
        editTextJumlahKebutuhanDarah = findViewById(R.id.editTextJumlahKebutuhanDarah);
        editTextNomorTelepon = findViewById(R.id.editTextNomorTelepon);
        editTextRumahSakit = findViewById(R.id.editTextRumahSakit);
        editTextLokasiRumahSakit = findViewById(R.id.editTextLokasiRumahSakit);

        Button btnTambahRiwayat = findViewById(R.id.btSimpanTR);
        btnTambahRiwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tambahDataKeFirebase("Donor Selesai", R.drawable.success);
                Intent toRiwayat = new Intent(TambahRiwayatActivity.this, RiwayatDonorActivity.class);
                startActivity(toRiwayat);
            }
        });
    }

    private void tambahDataKeFirebase(String status, int imageDrawableId) {
        String namaPencari = editTextNamaPencari.getText().toString().trim();
        String tanggalDonor = editTextTanggalDonor.getText().toString().trim();
        String golonganDarah = editTextGolonganDarah.getText().toString().trim();
        String jumlahKebutuhanDarah = editTextJumlahKebutuhanDarah.getText().toString().trim();
        String nomorTelepon = editTextNomorTelepon.getText().toString().trim();
        String rumahSakit = editTextRumahSakit.getText().toString().trim();
        String lokasiRumahSakit = editTextLokasiRumahSakit.getText().toString().trim();

        RiwayatDonor riwayatDonor = new RiwayatDonor();
        riwayatDonor.setName(namaPencari);
        riwayatDonor.setTanggalDonor(tanggalDonor);
        riwayatDonor.setGoldar(golonganDarah);
        riwayatDonor.setJumlahKebutuhanDarah(jumlahKebutuhanDarah);
        riwayatDonor.setNotelp(nomorTelepon);
        riwayatDonor.setRumahSakit(rumahSakit);
        riwayatDonor.setLokasiRumahSakit(lokasiRumahSakit);
        riwayatDonor.setStatus(status);

        if (status.equals("Donor Selesai")) {
            riwayatDonor.setImage(imageDrawableId);

            showNotification("Pendaftaran Anda Diterima", "Terima kasih telah mendaftar sebagai donor!");
        }

        String idRiwayat = databaseReference.push().getKey();

        databaseReference.child(idRiwayat).setValue(riwayatDonor);

        Toast.makeText(this, "Riwayat Donor Ditambahkan", Toast.LENGTH_SHORT).show();
    }

    private void showNotification(String title, String content) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("channel_id", "Channel Name", NotificationManager.IMPORTANCE_DEFAULT);
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel_id")
                    .setSmallIcon(R.drawable.success)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

            notificationManager.notify(1, builder.build());
        } else {
            Log.e("PermissionError", "VIBRATE or INTERNET permission is not granted");
        }
    }
}
