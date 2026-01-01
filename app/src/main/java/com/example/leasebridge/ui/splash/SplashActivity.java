package com.example.leasebridge.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.leasebridge.MainActivity;
import com.example.leasebridge.R;

public class SplashActivity extends AppCompatActivity {

    private ProgressBar loadingBar;
    private int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        loadingBar = findViewById(R.id.loadingBar);

        new Thread(() -> {
            while (progress < 100) {
                progress += 2;
                runOnUiThread(() -> loadingBar.setProgress(progress));

                try {
                    Thread.sleep(35);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }).start();
    }
}
