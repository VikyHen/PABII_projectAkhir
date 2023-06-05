package com.if4a.healthyfood.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.if4a.healthyfood.API.APIRequestData;
import com.if4a.healthyfood.API.RetroServer;
import com.if4a.healthyfood.Adapter.ManfaatViewAdapter;
import com.if4a.healthyfood.Model.ModelFood;
import com.if4a.healthyfood.Model.ModelManfaat;
import com.if4a.healthyfood.Model.ModelResponse;
import com.if4a.healthyfood.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    private TextView tvNama, tvDeskripsi, tvKalori;
    private ImageView ivGambar;
    private List<ModelManfaat> listManfaat;
    private RecyclerView rvManfaat;
    private ImageButton btnTambah;

    private RecyclerView.Adapter adapterManfaat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvNama = findViewById(R.id.tv_nama);
        tvDeskripsi = findViewById(R.id.tv_deskripsi);
        tvKalori = findViewById(R.id.tv_kalori);
        ivGambar = findViewById(R.id.iv_detail);

        rvManfaat = findViewById(R.id.rv_manfaat);
        rvManfaat.setHasFixedSize(true);
        rvManfaat.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        Intent intent = getIntent();

        String id = intent.getStringExtra("varId");
        String nama = intent.getStringExtra("varNama");
        String deskripsi = intent.getStringExtra("varDeskripsi");
        String kalori = intent.getStringExtra("varKalori");
        String gambar = intent.getStringExtra("varGambar");

        tvNama.setText(nama);
        tvDeskripsi.setText(deskripsi);
        tvKalori.setText(kalori + " kcal");
        if (intent.getStringExtra("varGambar").isEmpty()) {
            ivGambar.setImageResource(R.drawable.ic_person);
        } else {
            Picasso.get().load(gambar).into(ivGambar);
        }

        btnTambah = findViewById(R.id.btn_tambah);

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, TambahManfaatActivity.class);
                intent.putExtra("varId", id);

                startActivity(intent);
            }
        });

        retrieveManfaat(id);
    }

    private void retrieveManfaat(String id){
        APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = ARD.ardRetrieve();
        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                List<ModelFood> modelFoodList = response.body().getData();

                listManfaat = new ArrayList<>();
                for (ModelFood modelFood : modelFoodList) {
                    List<ModelManfaat> manfaatList = modelFood.getListManfaat();
                    for (ModelManfaat manfaat : manfaatList) {
                        if (manfaat.getFood_id() != null && id != null && manfaat.getFood_id().equals(id)) {
                            listManfaat.add(manfaat);
                        }
                    }
                }
                adapterManfaat = new ManfaatViewAdapter(listManfaat);
                adapterManfaat.notifyDataSetChanged();
                rvManfaat.setAdapter(adapterManfaat);
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {

            }
        });
    }
}