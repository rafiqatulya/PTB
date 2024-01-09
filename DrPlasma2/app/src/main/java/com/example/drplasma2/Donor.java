package com.example.drplasma2;

public class Donor {
    private String namaPendonor;
    private String golonganDarah;
    private String noTelepon;
    private String namaPencari;

    public Donor() {

    }

    public Donor(String namaPendonor, String golonganDarah, String noTelepon, String namaPencari) {
        this.namaPendonor = namaPendonor;
        this.golonganDarah = golonganDarah;
        this.noTelepon = noTelepon;
        this.namaPencari = namaPencari;
    }

    public String getNamaPendonor() {
        return namaPendonor;
    }

    public String getGolonganDarah() {
        return golonganDarah;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public String getNamaPencari() {
        return namaPencari;
    }
}
