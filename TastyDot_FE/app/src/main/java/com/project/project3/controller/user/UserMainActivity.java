package com.project.project3.controller.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.project.project3.R;

public class UserMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        BottomNavigationView bnv = findViewById(R.id.bvMain);
        FrameLayout fl = findViewById(R.id.fl);

        getSupportFragmentManager().beginTransaction().replace(
                // 1) 어디에
                R.id.fl,
                // 2) 어떤 부분화면으로
                new UserHomeFragment()
        ).commit();

        // bnv에 어떤 항목을 클릭했는지 구분한 다음
        // 해당 Fragment가 FrameLayout에 갈아 끼워질 수 있도록 만들자
        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // itme  : 내가 클릭한 항목에 대한 정보
                // 정보 : 속성,id,title,icon...
                // item.getItemId() : 항목의 id값을 가져오는 방법
                int itemId = item.getItemId();
                if(itemId == R.id.bnv_map){
                    getSupportFragmentManager().beginTransaction().replace(
                            R.id.fl,
                            new MapFragment()
                    ).commit();
                } else if (itemId == R.id.bnv_search) {
                    getSupportFragmentManager().beginTransaction().replace(
                            R.id.fl,
                            new UserSearchFragment()
                    ).commit();
                } else if (itemId == R.id.bnv_home) {
                    getSupportFragmentManager().beginTransaction().replace(
                            R.id.fl,
                            new UserHomeFragment()
                    ).commit();
                } else if (itemId == R.id.bnv_coupon) {
                    getSupportFragmentManager().beginTransaction().replace(
                            R.id.fl,
                            new UserCouponFragment()
                    ).commit();
                } else if (itemId == R.id.bnv_info) {
                    getSupportFragmentManager().beginTransaction().replace(
                            R.id.fl,
                            new UserInfoFragment()
                    ).commit();
                }

                // 항목에 대한 클릭 이벤트를 감지
                // false : 클릭한번하고 이벤트가 계속 된다고 생각함
                // true : 클릭 후 이벤트 종료
                return true;
            }
        });

    }


}