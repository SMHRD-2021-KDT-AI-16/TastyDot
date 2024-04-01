package com.project.project3.controller.advertiser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.kakao.util.maps.helper.Utility;
import com.project.project3.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


public class UserActivity extends AppCompatActivity {



    Intent intent;
    String id;
    String userId;
    String userName;
    String userEmail;
    TextView tvID;
    TextView tvStore;
    int storeIdx;
    ImageView imgStore;

    static RequestQueue requestQueue;
    private RequestManager glide;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);





        // 사용자의 정보를 불러와 변수에 저장
        intent = getIntent();
        id = intent.getStringExtra("id");
        userId = intent.getStringExtra("userId");
        userName = intent.getStringExtra("userName");
        userEmail = intent.getStringExtra("userEmail");


        loadStore(); // 사용자의 가게 정보를 불러오는 메서드

        TextView tvMenuAdd = findViewById(R.id.tvMenuAdd); // 메뉴 추가
        TextView tvMenuMod = findViewById(R.id.tvMenuMod); // 메뉴 수정 페이지x
        TextView tvAddCoupon = findViewById(R.id.tvAddCoupon); // 쿠폰 발급
        TextView tvNowCoupon = findViewById(R.id.tvNowCoupon); // 쿠폰 현황
        TextView tvUsedCoupon = findViewById(R.id.tvUsedCoupon); // 이전 쿠폰 내역x
         tvID = findViewById(R.id.tvId); // 사용자 이름
         tvStore = findViewById(R.id.tvStore); // 사용자 가게 이름
        imgStore = findViewById(R.id.imgStore);


        tvID.setText(userName);


        // 메뉴 추가 페이지 이동
        tvMenuAdd.setOnClickListener(v -> {
            intent = new Intent(this, MenuRegiActivity.class);
            Log.d("storename", String.valueOf(storeIdx));
            intent.putExtra("storeIdx", storeIdx);
            startActivity(intent);
        });

        // 메뉴 수정 페이지 이동
        tvMenuMod.setOnClickListener(v -> {
            intent = new Intent(this, MenuModActivity.class);
            intent.putExtra("storeIdx", storeIdx);
            startActivity(intent);
        });


        // 쿠폰 발급 페이지 이동
        tvAddCoupon.setOnClickListener(v->{
            intent = new Intent(this, AddCouponActivity.class);
            intent.putExtra("storeIdx", storeIdx);

            startActivity(intent);
        });


        // 쿠폰 현황 페이지 이동
        tvNowCoupon.setOnClickListener(v->{
            intent = new Intent(this, CheckCouponActivity.class);
            intent.putExtra("storeIdx", storeIdx);

            startActivity(intent);
        });

        // 이전 쿠폰 내역 페이지 이동
        tvUsedCoupon.setOnClickListener(v -> {
            intent = new Intent(this, HistoryCouponActivity.class);
            intent.putExtra("storeIdx", storeIdx);

            startActivity(intent);
        });

        }


        // 사용자의 가게 정보를 불러오는 메서드
        // id가 ? 인 사용자의 가게 정보를 불러오는 로직 만들기
        public void loadStore(){
            if(requestQueue == null){
                requestQueue = Volley.newRequestQueue(getApplicationContext());
            }
            String url = "http://192.168.0.25:8081/api/searchStore";

            StringRequest request = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {

                                JSONObject jsonResponse = new JSONObject(response);
                                // 유저에서 다 가게로 바꿀 것
                                 storeIdx = jsonResponse.getInt("store_idx");
                                String storeName = jsonResponse.getString("storeName");
                                String storeDesc = jsonResponse.getString("storeDesc");
                                String storeImg = jsonResponse.getString("storeImg");
                                Double storeLat = jsonResponse.getDouble("lat");
                                Double storeLng = jsonResponse.getDouble("lng");
                                int id = jsonResponse.getInt("id");
                                Log.d("storename",storeName);

                                tvStore.setText(storeName);
                                // 이미지를 가져와서 ImageView에 설정
                                Glide.with(UserActivity.this)
                                        .load(storeImg) // 이미지 URL 설정
                                        .into(imgStore);   // ImageView에 이미지 설정

                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("error", "볼리통신 에러남");
                        }
                    }
            ) {
                @Override
                public byte[] getBody() throws AuthFailureError {
                    JSONObject jsonBody = new JSONObject();
                    try {
                        jsonBody.put("id", id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return jsonBody.toString().getBytes();
                }

                public Map<String, String> getHeaders() throws AuthFailureError {
                    // 요청 헤더 설정
                    Map<String, String> headers = super.getHeaders();
                    if (headers == null || headers.isEmpty()) {
                        headers = new HashMap<>();
                    }
                    headers.put("Accept", "application/json");
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };
            request.setShouldCache(false);
            requestQueue.add(request);




        }


}