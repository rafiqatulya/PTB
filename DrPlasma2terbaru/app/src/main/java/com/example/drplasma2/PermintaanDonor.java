package com.example.drplasma2;

//mengorganisir dan membawa data permintaan donor ke recycler view
public class PermintaanDonor {
    private String namaPencari;
    private String golonganDarah;
    private String jumlahKebutuhanDarah;
    private String nomorTelepon;
    private String rumahSakit;
    private String lokasiRumahSakit;

    private int id_permintaan;

    public PermintaanDonor() {
    }

    // Getter and Setter methods
    public String getNamaPencari() {
        return namaPencari;
    }

    public void setNamaPencari(String namaPencari) {
        this.namaPencari = namaPencari;
    }

    public String getGolonganDarah() {
        return golonganDarah;
    }

    public void setGolonganDarah(String golonganDarah) {
        this.golonganDarah = golonganDarah;
    }

    public String getJumlahKebutuhanDarah() {
        return jumlahKebutuhanDarah;
    }

    public void setJumlahKebutuhanDarah(String jumlahKebutuhanDarah) {
        this.jumlahKebutuhanDarah = jumlahKebutuhanDarah;
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public void setNomorTelepon(String nomorTelepon) {
        this.nomorTelepon = nomorTelepon;
    }

    public String getRumahSakit() {
        return rumahSakit;
    }

    public void setRumahSakit(String rumahSakit) {
        this.rumahSakit = rumahSakit;
    }

    public String getLokasiRumahSakit() {
        return lokasiRumahSakit;
    }

    public void setLokasiRumahSakit(String lokasiRumahSakit) {
        this.lokasiRumahSakit = lokasiRumahSakit;
    }

    public int getIdPermintaan() {
        return id_permintaan;
    }
}
