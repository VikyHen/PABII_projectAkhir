package com.if4a.healthyfood.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.if4a.healthyfood.API.APIRequestData;
import com.if4a.healthyfood.API.RetroServer;
import com.if4a.healthyfood.Model.ModelResponse;
import com.if4a.healthyfood.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahManfaatActivity extends AppCompatActivity {
    private TextInputLayout ilKandungan, ilManfaatKandungan, ilJumlah;
    private TextInputEditText etKandungan, etManfaatKandungan, etJumlah;
    private MaterialButton btnTambah;
    private String kandungan, manfaatKandungan, jumlah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_manfaat);

        ilKandungan = findViewById(R.id.il_kandungan);
        ilManfaatKandungan = findViewById(R.id.il_manfaatKandungan);
        ilJumlah = findViewById(R.id.il_jumlah);

        etKandungan = findViewById(R.id.et_kandungan);
        etManfaatKandungan= findViewById(R.id.et_manfaatKandungan);
        etJumlah = findViewById(R.id.et_jumlah);

        btnTambah = findViewById(R.id.btn_tambah);

        Intent intent = getIntent();
        String food_id = intent.getStringExtra("varId");

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kandungan = etKandungan.getText().toString();
                manfaatKandungan = etManfaatKandungan.getText().toString();
                jumlah = etJumlah.getText().toString();

                if (kandungan.trim().isEmpty()){
                    ilKandungan.setError("Nama Kandungan Wajib Diisi");
                    return;
                }
                if (manfaatKandungan.trim().isEmpty()){
                    ilManfaatKandungan.setError("Manfaat Wajib Diiisi");
                    return;
                }
                if (jumlah.trim().isEmpty()){
                    ilJumlah.setError("Jumlah Kandungan Wajib Diisi");
                    return;
                }

                APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
                Call<ModelResponse> proses = ARD.ardCreateManfaat(food_id, kandungan, manfaatKandungan, jumlah);

                proses.enqueue(new Callback<ModelResponse>() {
                    @Override
                    public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                        Toast.makeText(TambahManfaatActivity.this, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ModelResponse> call, Throwable t) {
                        Toast.makeText(TambahManfaatActivity.this, "Gagal terhubung ke server : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
    }
}