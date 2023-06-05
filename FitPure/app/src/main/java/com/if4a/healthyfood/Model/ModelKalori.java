package com.if4a.healthyfood.Model;

import java.util.List;

public class ModelKalori {
    private String kode, pesan;
    private String jumlah_kalori;
    private List<ModelData> data;

    public String getKode() {
        return kode;
    }

    public String getPesan() {
        return pesan;
    }

    public String getJumlah_kalori() {
        return jumlah_kalori;
    }

    public List<ModelData> getData() {
        return data;
    }
}
