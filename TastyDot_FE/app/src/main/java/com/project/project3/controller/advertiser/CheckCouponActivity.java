package com.project.project3.controller.advertiser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.project.project3.R;

import org.json.JSONException;
import org.json.JSONObject;

public class CheckCouponActivity extends AppCompatActivity {
    Intent intent;
    int store_idx;

    TextView tvUse1000;
    TextView tvRem1000;
    TextView tvUse2000;
    TextView tvRem2000;
    TextView tvUse3000;
    TextView tvRem3000;
    TextView tvUse5000;
    TextView tvRem5000;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_coupon);
        intent = getIntent();
        store_idx = intent.getIntExtra("storeIdx", 1);
        // 사용한 쿠폰
        tvUse1000 = findViewById(R.id.tvUse1000);
        tvUse2000 = findViewById(R.id.tvUse2000);
        tvUse3000 = findViewById(R.id.tvUse3000);
        tvUse5000 = findViewById(R.id.tvUse5000);
        // 남은 쿠폰
        tvRem1000 = findViewById(R.id.tvRem1000);
        tvRem2000 = findViewById(R.id.tvRem2000);
        tvRem3000 = findViewById(R.id.tvRem3000);
        tvRem5000 = findViewById(R.id.tvRem5000);


        searchCoupon();
        Button btnCheckCoupon = findViewById(R.id.btnJoin);
        btnCheckCoupon.setOnClickListener(v -> {

            // 클릭시 유저 액티비티로 이동
            finish();
        });

        ImageView imgInfo = findViewById(R.id.imginfo2);
        imgInfo.setOnClickListener(v -> {
            intent = new Intent(this, AdvInfoActivity.class);
            startActivity(intent);
        });

        ImageView imgMenu = findViewById(R.id.imghome2);
        imgMenu.setOnClickListener(v -> {
            intent = new Intent(this, UserActivity.class);
            startActivity(intent);
        });

    }

    public void searchCoupon() {
        String url = "http://192.168.219.101:8081/api/searchCoupons?store_idx=" + store_idx; // 쿠폰 현황 조회 API 엔드포인트
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        // 응답으로 받은 JSON 배열을 순회하면서 쿠폰 현황 정보 처리
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject couponStatus = response.getJSONObject(i);
                            int discountPrice = couponStatus.getInt("discountPrice");
                            int used = couponStatus.getInt("used");
                            int remaining = couponStatus.getInt("remaining");

                            // 할인 금액별로 적절한 텍스트뷰에 쿠폰 현황 표시
                            switch (discountPrice) {
                                case 1000:
                                    tvUse1000.setText(String.valueOf(used));
                                    tvRem1000.setText(String.valueOf(remaining));
                                    break;
                                case 2000:
                                    tvUse2000.setText(String.valueOf(used));
                                    tvRem2000.setText(String.valueOf(remaining));
                                    break;
                                case 3000:
                                    tvUse3000.setText(String.valueOf(used));
                                    tvRem3000.setText(String.valueOf(remaining));
                                    break;
                                case 5000:
                                    tvUse5000.setText(String.valueOf(used));
                                    tvRem5000.setText(String.valueOf(remaining));
                                    break;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "쿠폰 현황 정보를 불러오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    // 에러 처리 로직
                    Toast.makeText(getApplicationContext(), "쿠폰 현황 정보를 불러오는 데 실패했습니다: " + error.toString(), Toast.LENGTH_SHORT).show();
                });

        requestQueue.add(jsonArrayRequest);
    }
}