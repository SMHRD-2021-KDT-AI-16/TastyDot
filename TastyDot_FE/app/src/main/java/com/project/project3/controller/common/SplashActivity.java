package com.project.project3.controller.common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.project.project3.R;
import com.project.project3.controller.advertiser.MainActivity;
import com.project.project3.controller.advertiser.StandingActivity1;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(()->{
            Intent intent = new Intent(this, DivideActivity.class);
            startActivity(intent);
        },3000);
    }
}