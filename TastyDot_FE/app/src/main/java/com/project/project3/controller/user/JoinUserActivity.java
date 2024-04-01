package com.project.project3.controller.user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.project.project3.controller.advertiser.JoinActivity;
import com.project.project3.controller.advertiser.MenuRegiActivity;
import com.project.project3.controller.advertiser.UserActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JoinUserActivity extends AppCompatActivity {

    EditText name;
    EditText id;
    EditText pw;
    EditText email;
    CheckBox ckBox1;
    CheckBox ckBox2;
    CheckBox ckBox3;
    CheckBox ckBox4;
    CheckBox ckBox5;
    CheckBox ckBox6;
    Button btnIdCheck;
    TextView tvIdCheck;
    static RequestQueue requestQueue;

    Button btnJoin;


    private List<String> selectedChecks = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_user);

         name = findViewById(R.id.etUserName);
         id = findViewById(R.id.etUserId);
         pw = findViewById(R.id.etUserPw);
         email = findViewById(R.id.etUserEmail);
         ckBox1 = findViewById(R.id.cbKfood);
         ckBox2 = findViewById(R.id.cbCfood);
         ckBox3 = findViewById(R.id.cbJfood);
         ckBox4 = findViewById(R.id.cbWfood);
         ckBox5 = findViewById(R.id.cbSnack);
         ckBox6 = findViewById(R.id.cbCafe);
         tvIdCheck = findViewById(R.id.tvUserIdCheck);
         btnIdCheck = findViewById(R.id.btnUserIdCheck);
         btnJoin = findViewById(R.id.btnUserJoin);

        // 중복체크 버튼 클릭 시
        btnIdCheck.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                tvIdCheck.setText("");
                 checkRequest();
             }
         });

        // 회원가입 버튼 클릭 시
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog();
            }
        });


        // 체크박스 선택시 selectedChecks리스트에 저장
        ckBox1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedChecks.add("한식");
            } else {
                selectedChecks.remove("한식");
            }
        });
        ckBox2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedChecks.add("중식");
            } else {
                selectedChecks.remove("중식");
            }
        });
        ckBox3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedChecks.add("일식");
            } else {
                selectedChecks.remove("일식");
            }
        });
        ckBox4.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedChecks.add("양식");
            } else {
                selectedChecks.remove("양식");
            }
        });
        ckBox5.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedChecks.add("분식");
            } else {
                selectedChecks.remove("분식");
            }
        });
        ckBox6.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedChecks.add("카페");
            } else {
                selectedChecks.remove("카페");
            }
        });







    }

    void showDialog() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(JoinUserActivity.this)
                .setTitle("회원 가입")
                .setMessage("회원가입 하시겠습니까?")
                .setPositiveButton("네", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (name.getText().toString().isEmpty() || id.getText().toString().isEmpty() ||
                                pw.getText().toString().isEmpty() || email.getText().toString().isEmpty()) {
                            Toast.makeText(JoinUserActivity.this, "모든 필드를 채워주세요.", Toast.LENGTH_SHORT).show();
                        } else {
                            // 모든 필드가 채워져 있을 때만 회원가입 요청 실행
                            Log.d("JoinUserActivity", "Email: " + email.getText().toString());
                            joinRequest();
                            Toast.makeText(JoinUserActivity.this, "가입 완료", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(JoinUserActivity.this, UserLoginActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    }
                })

                .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(JoinUserActivity.this, "가입 취소.", Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }


    // 아이디 중복체크 메서드
    public void checkRequest() {
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        String url = "http://192.168.219.101:8081/api/userIdCheck";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonResponse = new JSONObject(response);
                            String userId = jsonResponse.getString("clientId");
                            String userPw = jsonResponse.getString("clientPw");
                            String userName = jsonResponse.getString("clientName");
                            String userEmail = jsonResponse.getString("clientEmail");
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
                    jsonBody.put("clientId", id.getText().toString());
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

    // 회원가입 메서드
    public void joinRequest(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        String url = "http://192.168.219.101:8081/api/userJoin";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String userId = jsonResponse.getString("clientId");
                            String userPw = jsonResponse.getString("clientPw");
                            String userName = jsonResponse.getString("clientName");
                            String userEmail = jsonResponse.getString("clientEmail");
                            // 파싱한 데이터를 활용하여 필요한 작업을 수행합니다.
                            // 예: TextView에 출력
//                            textView.append( userName+"님 안녕하세요!"+"\n");
//                            textView.append( "아이디 : " + userId+"\n");
//                            textView.append("비밀번호 : " + userPw +"\n");
//                            textView.append( "이메일 : " + userEmail + "\n");
                            Toast.makeText(JoinUserActivity.this,
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
                    jsonBody.put("clientId", id.getText().toString());
                    jsonBody.put("clientPw", pw.getText().toString());
                    jsonBody.put("clientName", name.getText().toString());
                    jsonBody.put("clientEmail", email.getText().toString());

                    // selectedChecks 리스트를 구분자를 사용한 문자열로 변환
                    String preferencesString = String.join(",", selectedChecks);
                    // 변환된 문자열을 JSONObject에 추가
                    jsonBody.put("clientPreferences", preferencesString);
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