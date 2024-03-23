package com.project.project3.controller.common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.project.project3.R;
import com.project.project3.controller.advertiser.MainActivity;
import com.project.project3.controller.user.UserLoginActivity;

public class DivideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divide);
        RadioButton user = findViewById(R.id.rdUser);
        RadioButton adver = findViewById(R.id.rdAdver);
        Button access = findViewById(R.id.btnAccess);

        access.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.isChecked()){
                    Intent intent = new Intent(DivideActivity.this, UserLoginActivity.class);
                    startActivity(intent);
                } else if (adver.isChecked()) {
                    Intent intent = new Intent(DivideActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(DivideActivity.this, "사용자나 광고주 중 하나를 선택해주세요", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}