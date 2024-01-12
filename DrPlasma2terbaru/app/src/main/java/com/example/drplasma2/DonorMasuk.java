package com.example.drplasma2;

import com.google.firebase.database.PropertyName;

public class DonorMasuk {

    // Properti-properti dari objek DonorMasuk
    private String ID_Permintaan;
    private String namaPencari;
    private String golonganDarah;
    private String jumlahKebutuhanDarah;
    private String nomorTelepon;
    private String rumahSakit;
    private String lokasiRumahSakit;

    // Konstruktor default tanpa parameter
    public DonorMasuk() {
    }

    // Konstruktor dengan parameter
    public DonorMasuk(String ID_Permintaan, String namaPencari, String golonganDarah,
                      String jumlahKebutuhanDarah, String nomorTelepon, String rumahSakit,
                      String lokasiRumahSakit) {
        this.ID_Permintaan = ID_Permintaan;
        this.namaPencari = namaPencari;
        this.golonganDarah = golonganDarah;
        this.jumlahKebutuhanDarah = jumlahKebutuhanDarah;
        this.nomorTelepon = nomorTelepon;
        this.rumahSakit = rumahSakit;
        this.lokasiRumahSakit = lokasiRumahSakit;
    }

    // Anotasi PropertyName untuk menghubungkan nama properti pada Firebase Database
    @PropertyName("ID_Permintaan")
    public String getID_Permintaan() {
        return ID_Permintaan;
    }

    // Anotasi PropertyName untuk menghubungkan nama properti pada Firebase Database
    @PropertyName("ID_Permintaan")
    public void setID_Permintaan(String ID_Permintaan) {
        this.ID_Permintaan = ID_Permintaan;
    }

    // Getter untuk properti namaPencari
    public String getNamaPencari() {
        return namaPencari;
    }

    // Getter untuk properti golonganDarah
    public String getGolonganDarah() {
        return golonganDarah;
    }

    // Getter untuk properti jumlahKebutuhanDarah
    public String getJumlahKebutuhanDarah() {
        return jumlahKebutuhanDarah;
    }

    // Getter untuk properti nomorTelepon
    public String getNomorTelepon() {
        return nomorTelepon;
    }

    // Getter untuk properti rumahSakit
    public String getRumahSakit() {
        return rumahSakit;
    }

    // Getter untuk properti lokasiRumahSakit
    public String getLokasiRumahSakit() {
        return lokasiRumahSakit;
    }
}
