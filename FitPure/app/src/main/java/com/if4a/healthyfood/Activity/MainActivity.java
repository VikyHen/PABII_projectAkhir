package com.if4a.healthyfood.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.if4a.healthyfood.Activity.Fragment.CaloriesFragment;
import com.if4a.healthyfood.Activity.Fragment.HomeFragment;
import com.if4a.healthyfood.Activity.Fragment.ProfilFragment;
import com.if4a.healthyfood.R;
import com.if4a.healthyfood.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        gantiFragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    gantiFragment(new HomeFragment());
                    break;
                case R.id.profil:
                    gantiFragment(new ProfilFragment());
                    break;
                case R.id.calories:
                    gantiFragment(new CaloriesFragment());
                    break;
            }
            return true;
        });
    }

    private void gantiFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}