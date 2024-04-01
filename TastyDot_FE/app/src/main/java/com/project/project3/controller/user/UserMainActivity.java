package com.project.project3.controller.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.project.project3.R;

public class UserMainActivity extends AppCompatActivity {

    Intent intent;
    String id;
    String clientId;
    String clientName;
    String clientEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        intent = getIntent();
        id = intent.getStringExtra("id");
        clientId = intent.getStringExtra("clientId");
        clientName = intent.getStringExtra("clientName");
        clientEmail = intent.getStringExtra("clientEmail");

        BottomNavigationView bnv = findViewById(R.id.bvMain);

        // 첫 화면 설정
        setFragment("home"); // 예시로 홈 화면을 첫 화면으로 설정

        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.bnv_map) {
                    setFragment("map");
                    return true;
                } else if (itemId == R.id.bnv_search) {
                    setFragment("search");
                    return true;
                } else if (itemId == R.id.bnv_home) {
                    setFragment("home");
                    return true;
                } else if (itemId == R.id.bnv_coupon) {
                    setFragment("coupon");
                    return true;
                } else if (itemId == R.id.bnv_info) {
                    setFragment("info");
                    return true;
                }
                return false;
            }
        });
    }

    private void setFragment(String tag) {
        Fragment fragment = null;

        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("clientId", clientId);
        bundle.putString("clientName", clientName);
        bundle.putString("clientEmail", clientEmail);

        switch (tag) {
            case "map":
                fragment = new MapFragment();
                break;
            case "search":
                fragment = new UserSearchFragment();
                break;
            case "home":
                fragment = new UserHomeFragment();
                break;
            case "coupon":
                fragment = new UserCouponFragment();
                break;
            case "info":
                fragment = new UserInfoFragment();
                break;
        }

        if (fragment != null) {
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl, fragment).commit();
        }
    }
}

