package com.if4a.healthyfood.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.if4a.healthyfood.API.APIRequestData;
import com.if4a.healthyfood.API.RetroServer;
import com.if4a.healthyfood.Activity.DetailActivity;
import com.if4a.healthyfood.Activity.UbahActivity;
import com.if4a.healthyfood.Activity.UbahManfaatActivity;
import com.if4a.healthyfood.Model.ModelFood;
import com.if4a.healthyfood.Model.ModelManfaat;
import com.if4a.healthyfood.Model.ModelResponse;
import com.if4a.healthyfood.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManfaatViewAdapter extends RecyclerView.Adapter<ManfaatViewAdapter.ViewHolder> {
    private List<ModelManfaat> listManfaat;

    public ManfaatViewAdapter(List<ModelManfaat> listManfaat) {
        this.listManfaat = listManfaat;
    }

    @NonNull
    @Override
    public ManfaatViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View varView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_manfaat, parent, false);

        return new ViewHolder(varView);
    }

    @Override
    public void onBindViewHolder(@NonNull ManfaatViewAdapter.ViewHolder holder, int position) {
        ModelManfaat mf = listManfaat.get(position);

        holder.tvId.setText(mf.getFood_id());
        holder.tvKandungan.setText(mf.getKandungan());
        holder.tvManfaatKandungan.setText(mf.getManfaatKandungan());
        holder.tvJumlah.setText(mf.getJumlah());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder pesan = new AlertDialog.Builder(holder.itemView.getContext());
                pesan.setTitle("Perhatian");
                pesan.setMessage("Operasi apa yang akan dilakukan?");
                pesan.setCancelable(true);

                pesan.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String id_manfaat = mf.getId_manfaat();

                        APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
                        Call<ModelResponse> proses = ARD.ardDeleteManfaat(id_manfaat);

                        proses.enqueue(new Callback<ModelResponse>() {
                            @Override
                            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                                String kode = response.body().getKode();
                                String pesan = response.body().getPesan();

                                Toast.makeText(holder.itemView.getContext(), "Kode : " + kode + ", Pesan : " + pesan , Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<ModelResponse> call, Throwable t) {
                                Toast.makeText(holder.itemView.getContext(), "Gagal menghubungi server", Toast.LENGTH_SHORT).show();

                            }
                        });
                        dialog.dismiss();
                    }
                });

                pesan.setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.itemView.getContext(), mf.getId_manfaat() + " Dipilih" , Toast.LENGTH_SHORT).show();

                        String id_manfaat = mf.getId_manfaat();
                        String kandungan = mf.getKandungan();
                        String manfaatKandungan = mf.getManfaatKandungan();
                        String jumlah = mf.getJumlah();

                        Intent intent = new Intent(holder.itemView.getContext(), UbahManfaatActivity.class);

                        intent.putExtra("varId", id_manfaat);
                        intent.putExtra("varKandungan", kandungan);
                        intent.putExtra("varManfaatKandungan", manfaatKandungan);
                        intent.putExtra("varJumlah", jumlah);

                        holder.itemView.getContext().startActivity(intent);
                    }
                });

                pesan.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return listManfaat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvKandungan, tvManfaatKandungan, tvJumlah;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_id);
            tvKandungan = itemView.findViewById(R.id.tv_kandungan);
            tvManfaatKandungan = itemView.findViewById(R.id.tv_manfaatKandungan);
            tvJumlah = itemView.findViewById(R.id.tv_jumlah);
        }
    }
}
