package com.project.project3.controller.advertiser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.project.project3.R;

public class StandingActivity4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standing4);
        // 5초 후에 다른 액티비티로 이동
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(StandingActivity4.this, UserActivity.class); // 이동할 다음 액티비티를 설정해주세요
                startActivity(intent);
                finish(); // 현재 액티비티를 종료합니다.
            }
        }, 5000); // 5초를 밀리초 단위로 설정합니다.

        findViewById(R.id.btn_quit1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StandingActivity4.this, StandingActivity1.class);
                startActivity(intent);
            }
        });
    };
}