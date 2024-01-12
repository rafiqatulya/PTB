package com.example.drplasma2;

import java.util.HashMap;
import java.util.Map;

public class RiwayatPermintaan {
    private String status;
    private String name;
    private String notelp;
    private String goldar;

    private int image;

    public RiwayatPermintaan(String status, String name, String notelp, String goldar, int image){
        this.status = status;
        this.name = name;
        this.notelp = notelp;
        this.goldar = goldar;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotelp() {
        return notelp;
    }

    public void setNotelp(String notelp) {
        this.notelp = notelp;
    }

    public String getStatus() {
        return status;
    }

    public String getGoldar() {
        return goldar;
    }

    public void setGoldar(String goldar) {
        this.goldar = goldar;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("status", status);
        result.put("name", name);
        result.put("notelp", notelp);
        result.put("goldar", goldar);
        result.put("image", image);
        return result;
    }
}
