package com.example.drplasma2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfilFragment extends Fragment {

    private DatabaseReference databaseReference;

    public ProfilFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        Button btnProfil = view.findViewById(R.id.btnProfil);

        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserData userData = getUserData();

                if (userData != null) {

                    saveUserDataToSharedPreferences(userData);

                    UserDataSingleton.getInstance().setUserData(userData);
                    Intent intent = new Intent(requireActivity(), EditProfileActivity.class);
                    startActivity(intent);
                } else {
                    Log.d("ProfilFragment", "UserData is null");
                }
            }
        });

        String currentUserId = LoginActivity.getCurrentUserId();
        Log.d("ProfilFragment", "Retrieved currentUserId: " + currentUserId);

        if (!currentUserId.isEmpty()) {
            DatabaseReference userRef = databaseReference.child(currentUserId);

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        TextView emailTextView = view.findViewById(R.id.emailProfile);
                        TextView nameTextView = view.findViewById(R.id.namaProfil);
                        TextView phoneNumberTextView = view.findViewById(R.id.noTlpProfil);
                        TextView tanggalLahirTextView = view.findViewById(R.id.tglLahirProfil);
                        TextView alamatTextView = view.findViewById(R.id.alamatProfil);
                        TextView tinggiBadanTextView = view.findViewById(R.id.tbProfil);
                        TextView beratBadanTextView = view.findViewById(R.id.beratBadanProfil);
                        TextView golonganDarahTextView = view.findViewById(R.id.goldarProfil);
                        ImageView photoProfileImageView = view.findViewById(R.id.photoprofile);

                        String email = dataSnapshot.child("email").getValue(String.class);
                        String name = dataSnapshot.child("name").getValue(String.class);
                        String phoneNumber = dataSnapshot.child("phoneNumber").getValue(String.class);
                        String tanggalLahir = dataSnapshot.child("tanggalLahir").getValue(String.class);
                        String alamat = dataSnapshot.child("alamat").getValue(String.class);
                        String tinggiBadan = dataSnapshot.child("tinggiBadan").getValue(String.class);
                        String beratBadan = dataSnapshot.child("beratBadan").getValue(String.class);
                        String golonganDarah = dataSnapshot.child("golonganDarah").getValue(String.class);
                        String photoUrl = dataSnapshot.child("photoUrl").getValue(String.class);

                        emailTextView.setText(email != null ? email : "Tidak ada data");
                        nameTextView.setText(name != null ? name : "Tidak ada data");
                        phoneNumberTextView.setText(phoneNumber != null ? phoneNumber : "Tidak ada data");
                        tanggalLahirTextView.setText(tanggalLahir != null ? tanggalLahir : "Tidak ada data");
                        alamatTextView.setText(alamat != null ? alamat : "Tidak ada data");
                        tinggiBadanTextView.setText(tinggiBadan != null ? tinggiBadan : "Tidak ada data");
                        beratBadanTextView.setText(beratBadan != null ? beratBadan : "Tidak ada data");
                        golonganDarahTextView.setText(golonganDarah != null ? golonganDarah : "Tidak ada data");

                        if (photoUrl != null && !photoUrl.isEmpty()) {
                            Picasso.get().load(photoUrl).into(photoProfileImageView);
                        }

                        Log.d("ProfilFragment", "Data berhasil diambil: " +
                                "\nEmail: " + email +
                                "\nNama: " + name +
                                "\nNo Telepon: " + phoneNumber +
                                "\nTanggal Lahir: " + tanggalLahir +
                                "\nAlamat: " + alamat +
                                "\nTinggi Badan: " + tinggiBadan +
                                "\nBerat Badan: " + beratBadan +
                                "\nGolongan Darah: " + golonganDarah);
                    } else {
                        Log.d("ProfilFragment", "Data tidak ditemukan");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("ProfilFragment", "Error: " + databaseError.getMessage());
                }
            });
        } else {
            Log.d("ProfilFragment", "currentUserId kosong");
        }

        TextView userIdTextView = view.findViewById(R.id.debugUserId);
        userIdTextView.setText("UserID: " + currentUserId);

        return view;
    }

    private void saveUserDataToSharedPreferences(UserData userData) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireActivity().getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("email", userData.getEmail());
        editor.putString("name", userData.getName());
        editor.putString("phoneNumber", userData.getPhoneNumber());
        editor.putString("tanggalLahir", userData.getTanggalLahir());
        editor.putString("alamat", userData.getAlamat());
        editor.putString("tinggiBadan", userData.getTinggiBadan());
        editor.putString("beratBadan", userData.getBeratBadan());
        editor.putString("golonganDarah", userData.getGolonganDarah());

        editor.apply();
    }

    private void openEditProfilePage() {
        Intent intent = new Intent(requireActivity(), EditProfileActivity.class);
        startActivity(intent);
    }

    public UserData getUserData() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireActivity().getApplicationContext());
        String email = sharedPreferences.getString("email", "");
        String name = sharedPreferences.getString("name", "");
        String phoneNumber = sharedPreferences.getString("phoneNumber", "");
        String tanggalLahir = sharedPreferences.getString("tanggalLahir", "");
        String alamat = sharedPreferences.getString("alamat", "");
        String tinggiBadan = sharedPreferences.getString("tinggiBadan", "");
        String beratBadan = sharedPreferences.getString("beratBadan", "");
        String golonganDarah = sharedPreferences.getString("golonganDarah", "");

        return new UserData(email, name, phoneNumber, tanggalLahir, alamat, tinggiBadan, beratBadan, golonganDarah);
    }
}

