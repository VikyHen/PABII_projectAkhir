package com.if4a.healthyfood.Activity.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.if4a.healthyfood.API.APIRequestData;
import com.if4a.healthyfood.API.RetroServer;
import com.if4a.healthyfood.Adapter.FoodViewAdapter;
import com.if4a.healthyfood.Adapter.KaloriViewAdapter;
import com.if4a.healthyfood.Model.ModelData;
import com.if4a.healthyfood.Model.ModelFood;
import com.if4a.healthyfood.Model.ModelKalori;
import com.if4a.healthyfood.Model.ModelResponse;
import com.if4a.healthyfood.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CaloriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CaloriesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CaloriesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CaloriesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CaloriesFragment newInstance(String param1, String param2) {
        CaloriesFragment fragment = new CaloriesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calories, container, false);
    }

    private TextView tvjumlah;
    private RecyclerView rvKalori;
    private RecyclerView.LayoutManager lmKalori;
    private KaloriViewAdapter adapterKalori;
    private ProgressBar progressBar;
    private List<ModelData> listKalori = new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvjumlah = view.findViewById(R.id.tv_jumlahKalori);
        rvKalori = view.findViewById(R.id.rv_kalori);
        progressBar = view.findViewById(R.id.progressBar);

        lmKalori = new LinearLayoutManager(getView().getContext(), LinearLayoutManager.VERTICAL, false);
        rvKalori.setLayoutManager(lmKalori);
    }

    @Override
    public void onResume() {
        super.onResume();
        retrieveKalori();
    }

    public void retrieveKalori() {
        progressBar.setVisibility(View.VISIBLE);

        APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelKalori> proses = ARD.ardRetrieveKalori();

        proses.enqueue(new Callback<ModelKalori>() {
            @Override
            public void onResponse(Call<ModelKalori> call, Response<ModelKalori> response) {
                listKalori = response.body().getData();

                adapterKalori= new KaloriViewAdapter(listKalori);
                rvKalori.setAdapter(adapterKalori);

                tvjumlah.setText("Jumlah Kalori : " + response.body().getJumlah_kalori() + " Kcal");

                adapterKalori.notifyDataSetChanged();

                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ModelKalori> call, Throwable t) {
                Toast.makeText(getContext(), "Gagal Menghubungi Server : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}