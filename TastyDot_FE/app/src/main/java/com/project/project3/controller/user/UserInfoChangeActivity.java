package com.project.project3.controller.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.project.project3.R;
import com.project.project3.controller.advertiser.StandingActivity1;
import com.project.project3.controller.advertiser.StandingActivity2;

public class UserInfoChangeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_change);

        findViewById(R.id.btn_quit1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoChangeActivity.this, UserInfoFragment.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoChangeActivity.this, UserInfoFragment.class);
                startActivity(intent);
            }
        });
    }
}