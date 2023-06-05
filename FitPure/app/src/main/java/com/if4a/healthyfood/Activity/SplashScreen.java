package com.if4a.healthyfood.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.if4a.healthyfood.R;

public class SplashScreen extends AppCompatActivity {
    private ProgressBar pbSplash;
    private int proses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        pbSplash = findViewById(R.id.pb_splashscreen);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                jalanSplash();
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        thread.start();
    }

    private void jalanSplash(){

        for (proses=20; proses<=100; proses = proses + 15){
            try {
                pbSplash.setProgress(proses);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}