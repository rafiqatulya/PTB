package com.example.drplasma2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        Button btnNext = findViewById(R.id.btnnext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserDataToFirebase();
            }
        });

        TextView tvLogin = findViewById(R.id.tvlogin);
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toLogin2 = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(toLogin2);
            }
        });
    }

    private void saveUserDataToFirebase() {
        String email = ((EditText) findViewById(R.id.etemail)).getText().toString().trim();
        String name = ((EditText) findViewById(R.id.etname)).getText().toString().trim();
        String phoneNumber = ((EditText) findViewById(R.id.ettelp)).getText().toString().trim();
        String password = ((EditText) findViewById(R.id.etpass)).getText().toString().trim();
        String password2 = ((EditText) findViewById(R.id.etpass2)).getText().toString().trim();

        String tanggalLahir = ((EditText) findViewById(R.id.ettanggallahir)).getText().toString().trim();
        String alamat = ((EditText) findViewById(R.id.etalamat)).getText().toString().trim();
        String tinggiBadan = ((EditText) findViewById(R.id.ettinggibadan)).getText().toString().trim();
        String beratBadan = ((EditText) findViewById(R.id.etberatbadan)).getText().toString().trim();
        String golonganDarah = ((EditText) findViewById(R.id.etgolongandarah)).getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(name) || TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(password) || TextUtils.isEmpty(password2)) {
            Toast.makeText(RegisterActivity.this, "Harap isi semua bidang", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(password2)) {
            Toast.makeText(RegisterActivity.this, "Password dan konfirmasi password tidak cocok", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = databaseReference.push().getKey();
        User user = new User(userId, email, name, phoneNumber, password, tanggalLahir, alamat, tinggiBadan, beratBadan, golonganDarah);

        databaseReference.child(userId).setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(RegisterActivity.this, "Registrasi berhasil", Toast.LENGTH_SHORT).show();

                        Intent toLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(toLogin);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, "Registrasi gagal", Toast.LENGTH_SHORT).show();

                        Log.e("FirebaseError", "Gagal menyimpan data ke Firebase", e);
                    }
                });
    }
}
