package com.project.project3.controller.advertiser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.project.project3.R;
import com.project.project3.databinding.ActivityStanding1Binding;

public class StandingActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standing1);

        findViewById(R.id.btn_next1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StandingActivity1.this, StandingActivity2.class);
                startActivity(intent);
            }
        });
    }
}