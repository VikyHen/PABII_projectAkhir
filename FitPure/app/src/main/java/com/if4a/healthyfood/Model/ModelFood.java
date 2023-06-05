package com.if4a.healthyfood.Model;

import java.util.List;

public class ModelFood {
    private String id, nama, gambar, deskripsi, kalori;
    private List<ModelManfaat> manfaat;

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getGambar() {
        return gambar;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getKalori() {
        return kalori;
    }

    public List<ModelManfaat> getListManfaat() {
        return manfaat;
    }
}
