package com.project.project3.controller.advertiser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.project.project3.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddCouponActivity extends AppCompatActivity {
    Intent intent;
    EditText et1000;
    EditText et2000;
    EditText et3000;
    EditText et5000;
    int store_idx;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coupon);

         et1000 = findViewById(R.id.et1000);
         et2000 = findViewById(R.id.et2000);
         et3000 = findViewById(R.id.et3000);
         et5000 = findViewById(R.id.et5000);

        intent = getIntent();
        store_idx =  intent.getIntExtra("storeIdx", 1);


        Button btnAddCoupon = findViewById(R.id.btnJoin);
        btnAddCoupon.setOnClickListener(v -> {
            addCoupon();
            Toast.makeText(this, "쿠폰이 발급 완료됐습니다.", Toast.LENGTH_SHORT).show();
            finish();

        });

        ImageView imgInfo = findViewById(R.id.imginfo1);
        imgInfo.setOnClickListener(v -> {
            intent = new Intent(this, AdvInfoActivity.class);
            startActivity(intent);
        });

        ImageView imgMenu = findViewById(R.id.imghome1);
        imgMenu.setOnClickListener(v -> {
            intent = new Intent(this, UserActivity.class);
            startActivity(intent);
        });


    }


    public void addCoupon() {
        String url = "http://192.168.219.101:8081/api/addCoupons"; // API 엔드포인트 수정 필요
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        // 응답 처리 로직
                        JSONObject jsonResponse = new JSONObject(response);
                        Toast.makeText(this, "쿠폰이 발급 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        // 쿠폰 등록 성공 응답 처리
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    // 에러 처리 로직
                    Toast.makeText(this, "쿠폰 발급 실패: " + error.toString(), Toast.LENGTH_SHORT).show();

                }
        ) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONArray couponsArray = new JSONArray(); // 쿠폰들을 담을 JSON 배열

                try {
                    // 각 쿠폰 정보를 JSONObject로 생성하고, JSONArray에 추가
                    int[] discountPrices = {1000, 2000, 3000, 5000};
                    EditText[] couponInputs = {et1000, et2000, et3000, et5000};

                    for (int i = 0; i < discountPrices.length; i++) {
                        String countString = couponInputs[i].getText().toString();
                        int count = countString.isEmpty() ? 0 : Integer.parseInt(countString);


                        for (int j = 0; j < count; j++) {
                            JSONObject coupon = new JSONObject();
                            coupon.put("discountPrice", discountPrices[i]);
                            coupon.put("store_idx", store_idx);
                            coupon.put("is_used", true); // 쿠폰 발급 시 사용되지 않은 상태로 설정
                            couponsArray.put(coupon);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return couponsArray.toString().getBytes(); // JSON 배열을 문자열로 변환하여 바이트 배열로 반환
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);
    }







}