package com.example.drplasma2;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

@IgnoreExtraProperties
public class User {
    private String userId;
    private String email;
    private String name;
    private String phoneNumber;
    private String password;
    private String tanggalLahir;
    private String alamat;
    private String tinggiBadan;
    private String beratBadan;
    private String golonganDarah;

    public User() {

    }

    public User(String userId, String email, String name, String phoneNumber, String password,
                String tanggalLahir, String alamat, String tinggiBadan, String beratBadan, String golonganDarah) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.tanggalLahir = tanggalLahir;
        this.alamat = alamat;
        this.tinggiBadan = tinggiBadan;
        this.beratBadan = beratBadan;
        this.golonganDarah = golonganDarah;
    }

    @PropertyName("userId")
    public String getUserId() {
        return userId;
    }

    @PropertyName("email")
    public String getEmail() {
        return email;
    }

    @PropertyName("name")
    public String getName() {
        return name;
    }

    @PropertyName("phoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @PropertyName("password")
    public String getPassword() {
        return password;
    }

    @PropertyName("tanggalLahir")
    public String getTanggalLahir() {
        return tanggalLahir;
    }

    @PropertyName("alamat")
    public String getAlamat() {
        return alamat;
    }

    @PropertyName("tinggiBadan")
    public String getTinggiBadan() {
        return tinggiBadan;
    }

    @PropertyName("beratBadan")
    public String getBeratBadan() {
        return beratBadan;
    }

    @PropertyName("golonganDarah")
    public String getGolonganDarah() {
        return golonganDarah;
    }
}
