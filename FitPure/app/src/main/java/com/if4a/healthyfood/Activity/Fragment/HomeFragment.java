package com.if4a.healthyfood.Activity.Fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.provider.SyncStateContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.SearchView;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.if4a.healthyfood.API.APIRequestData;
import com.if4a.healthyfood.API.RetroServer;
import com.if4a.healthyfood.Activity.TambahActivity;
import com.if4a.healthyfood.Adapter.FoodViewAdapter;
import com.if4a.healthyfood.Model.ModelFood;
import com.if4a.healthyfood.Model.ModelResponse;
import com.if4a.healthyfood.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private RecyclerView rvFood;
    private LinearLayoutManager lmFood;
    private FoodViewAdapter adapterFood;
    private SearchView svFood;
    private ProgressBar progressBar;
    private List<ModelFood> listFood = new ArrayList<>();
    private FloatingActionButton fabTambah;

    private TextView tvWelcome;
    private String[] texts = {"Selamat Datang", "Welcome", "ようこそ", "欢迎", "환영합니다"};
    private int currentIndex = 0;
    private Handler handler;
    private Runnable runnable;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        rvFood = view.findViewById(R.id.rv_food);
        progressBar = view.findViewById(R.id.progressBar);
        fabTambah = view.findViewById(R.id.fab_tambah);

        svFood = view.findViewById(R.id.sv_food);
        svFood.clearFocus();
        svFood.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        lmFood = new LinearLayoutManager(getView().getContext(), LinearLayoutManager.VERTICAL, false);
        rvFood.setLayoutManager(lmFood);

        fabTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getView().getContext(), TambahActivity.class));
            }
        });

        tvWelcome = view.findViewById(R.id.tv_welcome);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                animateTextView();
                currentIndex = (currentIndex + 1) % texts.length;
                handler.postDelayed(this, 2000);
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        retrieveFood();
        handler.postDelayed(runnable, 2000);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    public void retrieveFood() {
        progressBar.setVisibility(View.VISIBLE);

        APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = ARD.ardRetrieve();

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                listFood = response.body().getData();

                adapterFood = new FoodViewAdapter(listFood);
                rvFood.setAdapter(adapterFood);

                adapterFood.notifyDataSetChanged();

                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Gagal Menghubungi Server : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterList(String newText) {
        List<ModelFood> filteredList = new ArrayList<>();
        for (ModelFood modelFood : listFood){
            if(modelFood.getNama().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(modelFood);
            }
        }

        if (filteredList.isEmpty()){
            Toast.makeText(getView().getContext(), "Food not found", Toast.LENGTH_SHORT).show();
        }
        else{
            adapterFood.setFilteredList(filteredList);
        }
    }

    private void animateTextView() {
        tvWelcome.animate()
                .alpha(0f)
                .translationYBy(tvWelcome.getHeight())
                .setDuration(500)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        tvWelcome.setText(texts[currentIndex]);
                        tvWelcome.setTranslationY(-tvWelcome.getHeight());
                        tvWelcome.animate()
                                .alpha(1f)
                                .translationYBy(tvWelcome.getHeight())
                                .setDuration(500)
                                .setInterpolator(new AccelerateDecelerateInterpolator())
                                .setListener(null)
                                .start();
                    }
                })
                .start();
    }
}