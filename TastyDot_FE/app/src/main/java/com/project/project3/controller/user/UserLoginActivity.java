package com.project.project3.controller.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kakao.util.maps.helper.Utility;
import com.project.project3.R;
import com.project.project3.controller.advertiser.JoinActivity;
import com.project.project3.controller.advertiser.MainActivity;
import com.project.project3.controller.advertiser.StandingActivity1;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class UserLoginActivity extends AppCompatActivity {

    TextView tvUserJoin;
    Button userLogin;
    EditText etId;
    EditText etPw;
    static RequestQueue requestQueue;
    String id;
    String userName;
    String userId;
    String userPw;
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

         etId = findViewById(R.id.etUserLoginId);
         etPw = findViewById(R.id.etUserLoginPw);
         userLogin = findViewById(R.id.btnUserLogin);
        tvUserJoin = findViewById(R.id.tvUserJoin);

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        tvUserJoin.setOnClickListener(v -> {
            Intent intent = new Intent(UserLoginActivity.this, JoinUserActivity.class);
            startActivity(intent);
        });
        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               loginRequest();

            }
        });


    }

    public void loginRequest(){
        String url = "http://192.168.219.101:8081/api/userLogin";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            id = jsonResponse.getString("clientId");
                            userId = jsonResponse.getString("clientPw");
                            userName = jsonResponse.getString("clientName");
                            userEmail = jsonResponse.getString("clientEmail");

                            if (userName != null){
                                Toast.makeText(UserLoginActivity.this,"로그인 완료",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UserLoginActivity.this, UserMainActivity.class);
                                // 로그인 성공시
                                intent.putExtra("id", id);
                                intent.putExtra("clientId", userId);
                                intent.putExtra("clientName", userName);
                                intent.putExtra("clientEmail", userEmail);

                                Log.d("MainActivity", "clientName: " + userName);

                                startActivity(intent);
                            }else {
                                Toast.makeText(UserLoginActivity.this, "아이디 또는 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(UserLoginActivity.this, "아이디 또는 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Log.d("에러",error.getMessage());
                    }
                }
        ) {

            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("clientId", etId.getText().toString());
                    jsonBody.put("clientPw", etPw.getText().toString());
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