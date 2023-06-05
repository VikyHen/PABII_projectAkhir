package com.if4a.healthyfood.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.if4a.healthyfood.API.APIRequestData;
import com.if4a.healthyfood.API.RetroServer;
import com.if4a.healthyfood.Model.ModelData;
import com.if4a.healthyfood.Model.ModelKalori;
import com.if4a.healthyfood.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KaloriViewAdapter extends RecyclerView.Adapter<KaloriViewAdapter.ViewHolder> {
    private List<ModelData> listKalori;

    public KaloriViewAdapter(List<ModelData> listKalori) {
        this.listKalori = listKalori;
    }

    @NonNull
    @Override
    public KaloriViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View varView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_kalori, parent, false);

        return new KaloriViewAdapter.ViewHolder(varView);
    }

    @Override
    public void onBindViewHolder(@NonNull KaloriViewAdapter.ViewHolder holder, int position) {
        ModelData md = listKalori.get(position);

        holder.tvNama.setText(md.getNama());
        holder.tvKalori.setText(md.getKalori() + " Kcal");
        Picasso.get().load(md.getGambar()).into(holder.ivGambar);

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_kalori = md.getId_Kalori();

                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                builder.setTitle("Konfirmasi")
                        .setMessage("Apakah anda ingin menghapus makanan ini dari list?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
                                Call<ModelKalori> proses = ARD.ardDeleteKalori(id_kalori);

                                proses.enqueue(new Callback<ModelKalori>() {
                                    @Override
                                    public void onResponse(Call<ModelKalori> call, Response<ModelKalori> response) {
                                        Toast.makeText(holder.itemView.getContext(), "Makanan Dihapus Dari List", Toast.LENGTH_SHORT).show();
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
        return listKalori.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvId, tvKalori;
        ImageButton btnRemove;
        ImageView ivGambar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.tv_nama);
            tvId = itemView.findViewById(R.id.tv_id);
            tvKalori = itemView.findViewById(R.id.tv_kalori);
            btnRemove = itemView.findViewById(R.id.btn_hapus);
            ivGambar = itemView.findViewById(R.id.iv_gambar);
        }
    }
}
