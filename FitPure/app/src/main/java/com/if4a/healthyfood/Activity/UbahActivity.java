package com.if4a.healthyfood.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.if4a.healthyfood.API.APIRequestData;
import com.if4a.healthyfood.API.RetroServer;
import com.if4a.healthyfood.Model.ModelResponse;
import com.if4a.healthyfood.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahActivity extends AppCompatActivity {
    private TextInputLayout ilNama, ilDeskripsi, ilKalori;
    private TextInputEditText etNama, etDeskripsi, etKalori;
    private MaterialButton btnUbah, btnPilih;
    private String nama, deskripsi, kalori;
    private ImageView ivGambar;
    private Bitmap gambar;

    private static final int REQUEST_PERMISSION_STORAGE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah);

        ilNama = findViewById(R.id.il_nama);
        ilDeskripsi= findViewById(R.id.il_deskripsi);
        ilKalori = findViewById(R.id.il_kalori);

        etNama = findViewById(R.id.et_nama);
        etDeskripsi = findViewById(R.id.et_deskripsi);
        etKalori = findViewById(R.id.et_kalori);
        ivGambar = findViewById(R.id.iv_gambar);

        btnUbah = findViewById(R.id.btn_ubah);
        btnPilih = findViewById(R.id.btn_pilih);

        Intent intent = getIntent();
        String varId = intent.getStringExtra("varId");
        String varNama = intent.getStringExtra("varNama");
        String varGambar = intent.getStringExtra("varGambar");
        String varDeskripsi = intent.getStringExtra("varDeskripsi");
        String varKalori = intent.getStringExtra("varKalori");

        etNama.setText(varNama);
        etDeskripsi.setText(varDeskripsi);
        etKalori.setText(varKalori);
        if (intent.getStringExtra("varGambar").isEmpty()) {
            ivGambar.setImageResource(R.drawable.ic_person);
        } else {
            Picasso.get().load(varGambar).into(ivGambar);
        }

        btnPilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(UbahActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(UbahActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_STORAGE);
                } else {
                    pilihGambar();
                }
            }
        });

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = etNama.getText().toString();
                deskripsi = etDeskripsi.getText().toString();
                kalori = etKalori.getText().toString();

                if (nama.trim().equals(null)) {
                    ilNama.setError("Name makanan wajib diisi");
                    return;
                }
                if (deskripsi.trim().equals(null)) {
                    ilDeskripsi.setError("Deskripsi wajib diisi");
                    return;
                }
                if (kalori.trim().equals(null)) {
                    ilKalori.setError("Kalori wajib diisi");
                    return;
                }
                if (ivGambar == null) {
                    Toast.makeText(UbahActivity.this, "Silahkan Pilih Gambar", Toast.LENGTH_SHORT).show();
                    return;
                }

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                gambar.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                final String base64gambar = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
                Call<ModelResponse> proses = ARD.ardUpdate(varId, nama, base64gambar, deskripsi, Integer.parseInt(kalori));

                proses.enqueue(new Callback<ModelResponse>() {
                    @Override
                    public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                        Toast.makeText(UbahActivity.this, "Data Berhasil Diupdate", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ModelResponse> call, Throwable t) {
                        Toast.makeText(UbahActivity.this, "Gagal terhubung ke server : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
    }

    private void pilihGambar(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                gambar = BitmapFactory.decodeStream(inputStream);
                ivGambar.setImageBitmap(gambar);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}