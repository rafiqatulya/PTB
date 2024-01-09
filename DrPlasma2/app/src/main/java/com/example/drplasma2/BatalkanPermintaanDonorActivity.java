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
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BatalkanPermintaanDonorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batalkan_permintaan_donor);

        String idPermintaan = getIntent().getStringExtra("ID_PERMINTAAN");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("permintaan_donor");
        DatabaseReference permintaanRef = databaseReference.child(idPermintaan);

        permintaanRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String golonganDarah = dataSnapshot.child("golonganDarah").getValue(String.class);
                String jumlahKebutuhanDarah = dataSnapshot.child("jumlahKebutuhanDarah").getValue(String.class);
                String namaPencari = dataSnapshot.child("namaPencari").getValue(String.class);
                String nomorTelepon = dataSnapshot.child("nomorTelepon").getValue(String.class);
                String rumahSakit = dataSnapshot.child("rumahSakit").getValue(String.class);
                String lokasiRumahSakit = dataSnapshot.child("lokasiRumahSakit").getValue(String.class);

                TextView golonganDarahTextView = findViewById(R.id.editTextGolonganDarah);
                golonganDarahTextView.setText(golonganDarah);

                TextView jumlahKebutuhanDarahTextView = findViewById(R.id.editTextJumlahKebutuhanDarah);
                jumlahKebutuhanDarahTextView.setText(jumlahKebutuhanDarah);

                TextView editTextNamaPencari = findViewById(R.id.editTextNamaPencari);
                editTextNamaPencari.setText(namaPencari);

                TextView editTextNomorTelepon = findViewById(R.id.editTextNomorTelepon);
                editTextNomorTelepon.setText(nomorTelepon);

                TextView editTextRumahSakit = findViewById(R.id.editTextRumahSakit);
                editTextRumahSakit.setText(rumahSakit);

                TextView editTextLokasiRumahSakit = findViewById(R.id.editTextLokasiRumahSakit);
                editTextLokasiRumahSakit.setText(lokasiRumahSakit);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Button batalCariButton = findViewById(R.id.batalcari);
        batalCariButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmationDialogBatalPermintaan(idPermintaan);
            }
        });
    }

    private void showConfirmationDialogBatalPermintaan(String idPermintaan) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View customDialogView = getLayoutInflater().inflate(R.layout.dialog_konfirmasi_batal_cari, null);
        builder.setView(customDialogView);

        AlertDialog dialog = builder.create();

        Button batalBatalpermintaanButton = customDialogView.findViewById(R.id.konfirmasibatalbatalcari);
        Button konfirmasipermintaanButton = customDialogView.findViewById(R.id.konfirmasibatalcari);

        konfirmasipermintaanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNotification("Pendaftaran Anda Ditolak", "Pendaftaran Anda ditolak, Silahkan coba lagi.");

                moveDataToRiwayatPermintaan(idPermintaan);

                Intent intent = new Intent(BatalkanPermintaanDonorActivity.this, RiwayatPermintaanActivity.class);
                startActivity(intent);

                dialog.dismiss();
            }
        });


        batalBatalpermintaanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void moveDataToRiwayatPermintaan(String idPermintaan) {
        DatabaseReference permintaanRef = FirebaseDatabase.getInstance().getReference("permintaan_donor").child(idPermintaan);
        DatabaseReference riwayatRef = FirebaseDatabase.getInstance().getReference("riwayat_permintaan").push();

        permintaanRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String golonganDarah = dataSnapshot.child("golonganDarah").getValue(String.class);
                String jumlahKebutuhanDarah = dataSnapshot.child("jumlahKebutuhanDarah").getValue(String.class);
                String namaPencari = dataSnapshot.child("namaPencari").getValue(String.class);
                String nomorTelepon = dataSnapshot.child("nomorTelepon").getValue(String.class);
                String rumahSakit = dataSnapshot.child("rumahSakit").getValue(String.class);
                String lokasiRumahSakit = dataSnapshot.child("lokasiRumahSakit").getValue(String.class);

                RiwayatPermintaan riwayatPermintaan = new RiwayatPermintaan("Donor Dibatalkan", namaPencari, nomorTelepon, golonganDarah, R.drawable.gagal);
                riwayatRef.setValue(riwayatPermintaan.toMap());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        permintaanRef.removeValue();
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
                    .setSmallIcon(R.drawable.gagal) // Replace with your notification icon
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
