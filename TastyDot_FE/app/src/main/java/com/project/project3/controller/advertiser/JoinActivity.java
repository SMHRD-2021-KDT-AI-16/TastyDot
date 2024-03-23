package com.project.project3.controller.advertiser;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.project.project3.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JoinActivity extends AppCompatActivity {

    EditText joinId;
    EditText joinPw;
    EditText joinName;
    EditText joinEmail;
    Button btnJoin;
    TextView tvIdCheck;
    Button btnIdCheck;

    static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        joinId = findViewById(R.id.etJoinUserId);
        joinPw = findViewById(R.id.etJoinUserPw);
        joinName = findViewById(R.id.etJoinUserName);
        joinEmail = findViewById(R.id.etJoinUserEmail);
         btnJoin = findViewById(R.id.btnJoin);
         tvIdCheck = findViewById(R.id.tvIdCheck);
         btnIdCheck = findViewById(R.id.btnIdCheck);

         //  아이디 중복검사 버튼 클릭시
        btnIdCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvIdCheck.setText("");
                // 중복체크 메서드 실행
                Log.d(" " , "중복체크 메서드 실행!!!!");
                checkRequest();}
        });


        // 회원가입 버튼 클릭시
        btnJoin.setOnClickListener(v -> {
            joinRequest();

            // 회원가입시 로그인페이지로 이동
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });


    }
    // 회원가입 메서드
    private void joinRequest() {
        String url = "http://192.168.219.101:8081/api/join";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String userId = jsonResponse.getString("userId");
                            String userPw = jsonResponse.getString("userPw");
                            String userName = jsonResponse.getString("userName");
                            String userEmail = jsonResponse.getString("userEmail");
                            // 파싱한 데이터를 활용하여 필요한 작업을 수행합니다.
                            // 예: TextView에 출력
//                            textView.append( userName+"님 안녕하세요!"+"\n");
//                            textView.append( "아이디 : " + userId+"\n");
//                            textView.append("비밀번호 : " + userPw +"\n");
//                            textView.append( "이메일 : " + userEmail + "\n");
                            Toast.makeText(JoinActivity.this,
                                    "회원가입 성공!", Toast.LENGTH_SHORT).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        println(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        println("에러-> " + error.getMessage());

                    }
                }

        ) {

            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("userId", joinId.getText().toString());
                    jsonBody.put("userPw", joinPw.getText().toString());
                    jsonBody.put("userName", joinName.getText().toString());
                    jsonBody.put("userEmail", joinEmail.getText().toString());
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



    // 아이디 중복체크 메서드
    public void checkRequest() {
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        String url = "http://192.168.219.101:8081/api/idCheck";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonResponse = new JSONObject(response);
                            String userId = jsonResponse.getString("userId");
                            String userPw = jsonResponse.getString("userPw");
                            String userName = jsonResponse.getString("userName");
                            String userEmail = jsonResponse.getString("userEmail");
                            // 아이디 값이 있을 떄
                            if (userId != null) {
                                tvIdCheck.append("이미 사용중인 아이디입니다.");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // 아이디가 없어 response객체가 null 값일 떄
                            tvIdCheck.append("사용가능한 아이디입니다");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tvIdCheck.append("에러-> "+ "\n" + error.getMessage());
                        Log.d("error", error.getMessage());
                    }
                }
        ) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("userId", joinId.getText().toString());
//                    jsonBody.put("password", joinPw.getText().toString());
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
//    println("요청보냄");
    }


    }
