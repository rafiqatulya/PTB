package com.example.drplasma2;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class EditProfileActivity extends AppCompatActivity {

    private Button buttonEditProfil;
    private View cardEditProfil;
    private DatabaseReference databaseReference;
    private ImageView photoProfileImageView;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        cardEditProfil = findViewById(R.id.cardVieweditprofil);
        buttonEditProfil = findViewById(R.id.btnEditProfil);
        photoProfileImageView = findViewById(R.id.photoprofile);

        fetchUserDataAndUpdateUI();

        photoProfileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });

        buttonEditProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserDataToFirebase();
                updateUserDataSingleton();

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        cardEditProfil.setVisibility(View.GONE);
                        buttonEditProfil.setVisibility(View.GONE);
                        openProfilFragment();

                        showToast("Data berhasil berubah");
                    }
                });
            }
        });

        fetchUserDataAndUpdateUI();
    }

    private void checkAndRequestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PICK_IMAGE_REQUEST);
                return;
            }
        }

        openImagePicker();
    }

    private void openImagePicker() {
        Log.d("EditProfileActivity", "Trying to open image picker");
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PICK_IMAGE_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImagePicker();
            } else {
                Toast.makeText(this, "Izin dibutuhkan untuk memilih gambar dari galeri", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            photoProfileImageView.setImageURI(selectedImageUri);
        }
    }

    private void fetchUserDataAndUpdateUI() {
        String currentUserId = LoginActivity.getCurrentUserId();

        if (currentUserId != null && !currentUserId.isEmpty()) {
            DatabaseReference userRef = databaseReference.child(currentUserId);

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String email = dataSnapshot.child("email").getValue(String.class);
                        String name = dataSnapshot.child("name").getValue(String.class);
                        String tanggalLahir = dataSnapshot.child("tanggalLahir").getValue(String.class);
                        String alamat = dataSnapshot.child("alamat").getValue(String.class);
                        String phoneNumber = dataSnapshot.child("phoneNumber").getValue(String.class);
                        String tinggiBadan = dataSnapshot.child("tinggiBadan").getValue(String.class);
                        String beratBadan = dataSnapshot.child("beratBadan").getValue(String.class);
                        String golonganDarah = dataSnapshot.child("golonganDarah").getValue(String.class);
                        String photoUrl = dataSnapshot.child("photoUrl").getValue(String.class);

                        if (photoUrl != null && !photoUrl.isEmpty()) {
                            Picasso.get().load(photoUrl).into(photoProfileImageView);
                        }

                        updateUI(email, name, tanggalLahir, alamat, phoneNumber, tinggiBadan, beratBadan, golonganDarah);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("EditProfileActivity", "Error: " + databaseError.getMessage());
                }
            });
        }
    }



    private void updateUI(String email, String name, String tanggalLahir, String alamat,
                          String phoneNumber, String tinggiBadan, String beratBadan, String golonganDarah) {
        EditText editTextEmail = findViewById(R.id.emailProfile);
        EditText editTextNama = findViewById(R.id.namaProfil);
        EditText editTextTanggalLahir = findViewById(R.id.tglLahirProfil);
        EditText editTextAlamat = findViewById(R.id.alamatProfil);
        EditText editTextNoTelp = findViewById(R.id.noTlpProfil);
        EditText editTextTinggiBadan = findViewById(R.id.tbProfil);
        EditText editTextBeratBadan = findViewById(R.id.beratBadanProfil);
        EditText editTextGolonganDarah = findViewById(R.id.goldarProfil);

        editTextEmail.setText(email);
        editTextNama.setText(name);
        editTextTanggalLahir.setText(tanggalLahir);
        editTextAlamat.setText(alamat);
        editTextNoTelp.setText(phoneNumber);
        editTextTinggiBadan.setText(tinggiBadan);
        editTextBeratBadan.setText(beratBadan);
        editTextGolonganDarah.setText(golonganDarah);
    }

    private void updateUserDataToFirebase() {
        String currentUserId = LoginActivity.getCurrentUserId();

        if (currentUserId != null && !currentUserId.isEmpty()) {
            DatabaseReference userRef = databaseReference.child(currentUserId);

            EditText editTextEmail = findViewById(R.id.emailProfile);
            EditText editTextNama = findViewById(R.id.namaProfil);
            EditText editTextTanggalLahir = findViewById(R.id.tglLahirProfil);
            EditText editTextAlamat = findViewById(R.id.alamatProfil);
            EditText editTextNoTelp = findViewById(R.id.noTlpProfil);
            EditText editTextTinggiBadan = findViewById(R.id.tbProfil);
            EditText editTextBeratBadan = findViewById(R.id.beratBadanProfil);
            EditText editTextGolonganDarah = findViewById(R.id.goldarProfil);

            String updatedEmail = editTextEmail.getText().toString();
            String updatedName = editTextNama.getText().toString();
            String updatedTanggalLahir = editTextTanggalLahir.getText().toString();
            String updatedAlamat = editTextAlamat.getText().toString();
            String updatedNoTelp = editTextNoTelp.getText().toString();
            String updatedTinggiBadan = editTextTinggiBadan.getText().toString();
            String updatedBeratBadan = editTextBeratBadan.getText().toString();
            String updatedGolonganDarah = editTextGolonganDarah.getText().toString();

            userRef.child("email").setValue(updatedEmail);
            userRef.child("name").setValue(updatedName);
            userRef.child("tanggalLahir").setValue(updatedTanggalLahir);
            userRef.child("alamat").setValue(updatedAlamat);
            userRef.child("phoneNumber").setValue(updatedNoTelp);
            userRef.child("tinggiBadan").setValue(updatedTinggiBadan);
            userRef.child("beratBadan").setValue(updatedBeratBadan);
            userRef.child("golonganDarah").setValue(updatedGolonganDarah);

            Uri selectedImageUri = getImageUri(photoProfileImageView);
            if (selectedImageUri != null) {
                String photoUrl = selectedImageUri.toString();
                userRef.child("photoUrl").setValue(photoUrl);
            }
        }
    }

    private Uri getImageUri(ImageView imageView) {
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }

    private void updateUserDataSingleton() {

    }

    private void openProfilFragment() {
        Fragment fragment = new ProfilFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.editprofil, fragment);

        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }

    private void setNonEditableEditText(EditText editText, String text) {
        editText.setText(text);
        editText.setFocusable(false);
        editText.setClickable(false);
        editText.setCursorVisible(false);
    }

    private void showToast(String message) {
        Toast.makeText(EditProfileActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
