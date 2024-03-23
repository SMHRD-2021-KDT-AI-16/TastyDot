package com.project.project3.controller.advertiser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.project.project3.R;

public class StandingActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standing3);
        findViewById(R.id.btn_next3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StandingActivity3.this, StandingActivity4.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_quit1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StandingActivity3.this, StandingActivity1.class);
                startActivity(intent);
            }
        });
    }
}