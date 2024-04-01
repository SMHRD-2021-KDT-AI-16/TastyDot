package com.project.project3.controller.advertiser;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
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
import com.project.project3.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class MenuRegiActivity extends AppCompatActivity {

    Intent intent;

    ImageView imgMenu;
    EditText menuName;
    EditText menuDesc;
    EditText menuPrice;
    ActivityResultLauncher<Intent> startActivityResult;
    static RequestQueue requestQueue;
    int storeIdx;

    String imgUrl;

    String encodedImage;

    Bitmap selectedImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_regi);
        menuName = findViewById(R.id.etMenuName); // 메뉴 이름
        menuDesc  = findViewById(R.id.etModExplain); // 상세 메뉴 내용
        imgMenu = findViewById(R.id.imgMenu); // 메뉴 사진
        menuPrice = findViewById(R.id.etPrice4); // 메뉴 가격

        intent = getIntent();
        storeIdx = intent.getIntExtra("storeIdx", 1);

        Button btnPicAdd = findViewById(R.id.btnPicAdd); // 사진 추가 버튼
        btnPicAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityResult.launch(intent);
            }
        });

        startActivityResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {

            // 원하는 너비와 높이 설정
            int newWidth = 300; // 예시로 300 픽셀로 설정
            int newHeight = 300; // 예시로 300 픽셀로 설정

            @Override
            public void onActivityResult(ActivityResult result) {
                if ( result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    try {
                        // 갤러리에서 이미지를 비트맵으로 변환
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

                        // 이미지를 원하는 크기로 조절
                        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);


                        // 압축된 이미지를 ImageView에 표시
                        imgMenu.setImageBitmap(resizedBitmap);

                        // Base64로 인코딩
                         encodedImage = encodeImage(resizedBitmap);
                        Log.d("Encoded Image", encodedImage);

                    }
                    catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        Button btnAddMenu = findViewById(R.id.btnAddMenu);
        btnAddMenu.setOnClickListener(v -> {
            showDialog();

        });

        // 홈화면 돌아가기
        ImageView imgInfo = findViewById(R.id.imginfo3);
        imgInfo.setOnClickListener(v -> {
            intent = new Intent(this, AdvInfoActivity.class);
            startActivity(intent);
        });

        // 홈화면 돌아가기2
        ImageView imgMenu = findViewById(R.id.imghome3);
        imgMenu.setOnClickListener(v -> {
            intent = new Intent(this, UserActivity.class);
            startActivity(intent);
        });

    }
    private String encodeImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); // JPEG 형식으로 압축하여 ByteArrayOutputStream에 쓰기
        byte[] imageBytes = baos.toByteArray(); // ByteArrayOutputStream을 byte 배열로 변환
        return Base64.encodeToString(imageBytes, Base64.DEFAULT); // Base64로 인코딩된 문자열 반환
    }

    void showDialog() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(MenuRegiActivity.this)
                .setTitle("메뉴 등록")
                .setMessage("메뉴를 등록하시겠습니까?")
                .setPositiveButton("네", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        uploadImage();
                        uploadMenu();
                        Toast.makeText(MenuRegiActivity.this, "메뉴가 등록됐습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                
                .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MenuRegiActivity.this, "메뉴를 등록하지 않았습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }

    public void uploadMenu() {
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        String url = "http://192.168.0.25:8081/api/addMenu";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String menuName = jsonResponse.getString("menuName");
                            String menuDesc = jsonResponse.getString("menuDesc");
                            int menuPrice = jsonResponse.getInt("menuPrice");
                            String menuImg = jsonResponse.getString("menuImg");

                            // 파싱한 데이터를 활용하여 필요한 작업을 수행합니다.
                            // 예: TextView에 출력


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
                    jsonBody.put("menuName", menuName.getText().toString());
                    jsonBody.put("menuDesc", menuDesc.getText().toString());
                    jsonBody.put("menuPrice", menuPrice.getText().toString());
                    jsonBody.put("menuImg", encodedImage);
                    jsonBody.put("imgUrl", "테스트용 이미지 경로2"); // imgUrl 로 바꿀 것
                    jsonBody.put("store_idx", storeIdx);
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

    // 서버에 이미지를 업로드 하는 메서드
    // imgUrl 경로도 설정해줘야함
    public void uploadImage(){

    }


}
