package com.example.drplasma2;

public class DonorMasuk {
    private String ID_Permintaan;
    private String namaPencari;
    private String golonganDarah;
    private String jumlahKebutuhanDarah;
    private String nomorTelepon;
    private String rumahSakit;
    private String lokasiRumahSakit;
    private String idPermintaan;

    public DonorMasuk() {

    }

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

    public String getID_Permintaan() {
        return ID_Permintaan;
    }

    public void setID_Permintaan(String ID_Permintaan) {
        this.ID_Permintaan = ID_Permintaan;
    }

    public String getNamaPencari() {
        return namaPencari;
    }

    public String getGolonganDarah() {
        return golonganDarah;
    }

    public String getJumlahKebutuhanDarah() {
        return jumlahKebutuhanDarah;
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public String getRumahSakit() {
        return rumahSakit;
    }

    public String getLokasiRumahSakit() {
        return lokasiRumahSakit;
    }

    public String getIdPermintaan() {
        return idPermintaan;
    }

}
