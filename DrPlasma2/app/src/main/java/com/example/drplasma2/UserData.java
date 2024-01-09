package com.example.drplasma2;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class UserData implements Parcelable {
    private String email;
    private String name;
    private String phoneNumber;
    private String tanggalLahir;
    private String alamat;
    private String tinggiBadan;
    private String beratBadan;
    private String golonganDarah;

    public UserData(String email, String name, String phoneNumber, String tanggalLahir, String alamat, String tinggiBadan, String beratBadan, String golonganDarah) {
        this.email = email != null ? email : "";
        this.name = name != null ? name : "";
        this.phoneNumber = phoneNumber != null ? phoneNumber : "";
        this.tanggalLahir = tanggalLahir != null ? tanggalLahir : "";
        this.alamat = alamat != null ? alamat : "";
        this.tinggiBadan = tinggiBadan != null ? tinggiBadan : "";
        this.beratBadan = beratBadan != null ? beratBadan : "";
        this.golonganDarah = golonganDarah != null ? golonganDarah : "";

        Log.d("UserData", "Constructor - Email: " + this.email);
        Log.d("UserData", "Constructor - Name: " + this.name);
    }


    protected UserData(Parcel in) {
        email = in.readString();
        name = in.readString();
        phoneNumber = in.readString();
        tanggalLahir = in.readString();
        alamat = in.readString();
        tinggiBadan = in.readString();
        beratBadan = in.readString();
        golonganDarah = in.readString();
    }

    public static final Creator<UserData> CREATOR = new Creator<UserData>() {
        @Override
        public UserData createFromParcel(Parcel in) {
            return new UserData(in);
        }

        @Override
        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getTinggiBadan() {
        return tinggiBadan;
    }

    public String getBeratBadan() {
        return beratBadan;
    }

    public String getGolonganDarah() {
        return golonganDarah;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(name);
        dest.writeString(phoneNumber);
        dest.writeString(tanggalLahir);
        dest.writeString(alamat);
        dest.writeString(tinggiBadan);
        dest.writeString(beratBadan);
        dest.writeString(golonganDarah);
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat != null ? alamat : "";
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber != null ? phoneNumber : "";
    }

    public void setEmail(String email) {
        this.email = email != null ? email : "";
    }

    public void setName(String name) {
        this.name = name != null ? name : "";
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir != null ? tanggalLahir : "";
    }

    public void setTinggiBadan(String tinggiBadan) {
        this.tinggiBadan = tinggiBadan != null ? tinggiBadan : "";
    }

    public void setBeratBadan(String beratBadan) {
        this.beratBadan = beratBadan != null ? beratBadan : "";
    }

    public void setGolonganDarah(String golonganDarah) {
        this.golonganDarah = golonganDarah != null ? golonganDarah : "";
    }
}
