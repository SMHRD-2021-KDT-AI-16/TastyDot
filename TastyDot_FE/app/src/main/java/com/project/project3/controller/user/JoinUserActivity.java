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

    Button join;


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
        join = findViewById(R.id.btnUserJoin);

         btnIdCheck.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                tvIdCheck.setText("");
                // 중복체크 메서드 실행
                 checkRequest();
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



        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
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
                        Toast.makeText(JoinUserActivity.this, "가입 완료.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(JoinUserActivity.this, UserLoginActivity.class);
                        startActivity(intent);
                        finish();
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
                    jsonBody.put("userId", id.getText().toString());
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