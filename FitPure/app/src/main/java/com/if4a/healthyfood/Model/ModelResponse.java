package com.if4a.healthyfood.Model;

import java.util.List;

public class ModelResponse {
    private String kode, pesan;
    private List<ModelFood> data;

    public String getKode() {
        return kode;
    }

    public String getPesan() {
        return pesan;
    }

    public List<ModelFood> getData() {
        return data;
    }
}
