package com.if4a.healthyfood.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.if4a.healthyfood.API.APIRequestData;
import com.if4a.healthyfood.API.RetroServer;
import com.if4a.healthyfood.Activity.DetailActivity;
import com.if4a.healthyfood.Activity.Fragment.HomeFragment;
import com.if4a.healthyfood.Activity.TambahActivity;
import com.if4a.healthyfood.Activity.UbahActivity;
import com.if4a.healthyfood.Model.ModelFood;
import com.if4a.healthyfood.Model.ModelKalori;
import com.if4a.healthyfood.Model.ModelResponse;
import com.if4a.healthyfood.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodViewAdapter extends RecyclerView.Adapter<FoodViewAdapter.ViewHolder> {
    private List<ModelFood> listFood;

    public FoodViewAdapter(List<ModelFood> listFood) {
        this.listFood = listFood;
    }

    public void setFilteredList(List<ModelFood> filteredList){
        this.listFood = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View varView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_food, parent, false);
        return new ViewHolder(varView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewAdapter.ViewHolder holder, int position) {
        ModelFood mk = listFood.get(position);

        if(position%2==0){
            holder.cvFood.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.first_item));
        }
        else{
            holder.cvFood.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.second_item));
        }

        holder.tvId.setText(mk.getId());
        holder.tvNama.setText(mk.getNama());
        holder.tvKalori.setText("Kalori : " + mk.getKalori() + " kcal");

        if(mk.getGambar().isEmpty()){
            holder.ivFood.setImageResource(R.drawable.ic_person);
        }
        else{
            Picasso.get().load(mk.getGambar()).into(holder.ivFood);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(), mk.getNama() + " Dipilih" , Toast.LENGTH_SHORT).show();

                String id = mk.getId();
                String nama = mk.getNama();
                String gambar = mk.getGambar();
                String deskripsi = mk.getDeskripsi();
                String kalori = mk.getKalori();

                Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);

                intent.putExtra("varId", id);
                intent.putExtra("varNama", nama);
                intent.putExtra("varGambar", gambar);
                intent.putExtra("varDeskripsi", deskripsi);
                intent.putExtra("varKalori", kalori);

                holder.itemView.getContext().startActivity(intent);
            }
        });

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
                        String id = mk.getId();

                        APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
                        Call<ModelResponse> proses = ARD.ardDelete(id);

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
                        Toast.makeText(holder.itemView.getContext(), mk.getNama() + " Dipilih" , Toast.LENGTH_SHORT).show();

                        String id = mk.getId();
                        String nama = mk.getNama();
                        String gambar = mk.getGambar();
                        String deskripsi = mk.getDeskripsi();
                        String kalori = mk.getKalori();

                        Intent intent = new Intent(holder.itemView.getContext(), UbahActivity.class);

                        intent.putExtra("varId", id);
                        intent.putExtra("varNama", nama);
                        intent.putExtra("varGambar", gambar);
                        intent.putExtra("varDeskripsi", deskripsi);
                        intent.putExtra("varKalori", kalori);

                        holder.itemView.getContext().startActivity(intent);
                    }
                });

                pesan.show();
                return true;
            }
        });

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int kalori = Integer.parseInt(mk.getKalori());
                String nama = mk.getNama();
                String gambar = mk.getGambar();

                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                builder.setTitle("Konfirmasi")
                        .setMessage("Apakah Anda ingin menambahkan makanan ini?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
                                Call<ModelKalori> proses = ARD.ardAddKalori(nama, gambar, kalori);

                                proses.enqueue(new Callback<ModelKalori>() {
                                    @Override
                                    public void onResponse(Call<ModelKalori> call, Response<ModelKalori> response) {
                                        String kode = response.body().getKode();
                                        String pesan = response.body().getPesan();

                                        Toast.makeText(holder.itemView.getContext(), pesan, Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<ModelKalori> call, Throwable t) {
                                        Toast.makeText(holder.itemView.getContext(), "Gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listFood.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
    TextView tvId, tvNama, tvKalori;
    ImageView ivFood;
    CardView cvFood;
    ImageButton btnAdd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_id);
            tvNama = itemView.findViewById(R.id.tv_nama);
            ivFood = itemView.findViewById(R.id.iv_food);
            tvKalori = itemView.findViewById(R.id.tv_kalori);
            cvFood = itemView.findViewById(R.id.cvFood);
            btnAdd = itemView.findViewById(R.id.btn_add);
        }
    }


}
