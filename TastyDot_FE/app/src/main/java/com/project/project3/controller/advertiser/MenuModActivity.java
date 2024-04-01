package com.project.project3.controller.advertiser;

import static android.opengl.ETC1.encodeImage;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.project3.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MenuModActivity extends AppCompatActivity {

    String[] items = {"메뉴1", "메뉴2", "메뉴3", "메뉴4"};

    ArrayList<String> itemList = new ArrayList<>() ;
    Intent intent;
    int storeIdx;

    ImageView imgMenu;

    static RequestQueue requestQueue;
    String encodedImage;


    ActivityResultLauncher<Intent> startActivityResult;
    Spinner spNowMenu;
    EditText etModMenu;
    EditText etModPrice;
    EditText etModExplain;
    ArrayList<HashMap<String, Object>> menuList = new ArrayList<>();
    ArrayList<String> menuNames = new ArrayList<>(); // 스피너에 표시될 메뉴 이름 리스트
    ArrayAdapter<String> adapter; // 스피너에 표시될 메뉴 이름을 담을 어댑터


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_mod);

         etModMenu = findViewById(R.id.etModMenu);
         etModPrice = findViewById(R.id.etModPrice);
         etModExplain = findViewById(R.id.etModExplain);

        intent = getIntent();
        storeIdx = intent.getIntExtra("storeIdx", 1); // 가게 식별자 가져오기
        Log.d("test", String.valueOf(storeIdx));


        // Volley 요청 큐 초기화
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        spNowMenu = findViewById(R.id.spNowMenu);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, menuNames);
        spNowMenu.setAdapter(adapter);

        getItems();


        spNowMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                HashMap<String, Object> selectedMenu = menuList.get(position);
                if(selectedMenu != null) {
                    String menuName = selectedMenu.get("menuName") != null ? selectedMenu.get("menuName").toString() : "";
                    String menuPrice = selectedMenu.get("menuPrice") != null ? selectedMenu.get("menuPrice").toString() : "";
                    String menuDesc = selectedMenu.get("menuDesc") != null ? selectedMenu.get("menuDesc").toString() : "";


                    etModPrice.setText(menuPrice);
                    etModExplain.setText(menuDesc);
                    // 이미지 처리 등 추가적인 코드
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle case where nothing is selected
            }
        });



        EditText menuName = findViewById(R.id.etModMenu);
        EditText menuPrice = findViewById(R.id.etModPrice);
        EditText menuExplain = findViewById(R.id.etModExplain);
        Button btnModPic = findViewById(R.id.btnPicMod);
        Button btnMod = findViewById(R.id.btnModMenu);
        imgMenu = findViewById(R.id.imgModMenu);

        ImageView imgInfo = findViewById(R.id.imgInfo4);
        imgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MenuModActivity.this, AdvInfoActivity.class);
                startActivity(intent);
            }
        });
        ImageView imgHome = findViewById(R.id.imgHome4);
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MenuModActivity.this,UserActivity.class);
                startActivity(intent);
            }
        });

        btnModPic.setOnClickListener(new View.OnClickListener() {
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

        btnMod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });




    }

    private String encodeImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); // JPEG 형식으로 압축하여 ByteArrayOutputStream에 쓰기
        byte[] imageBytes = baos.toByteArray(); // ByteArrayOutputStream을 byte 배열로 변환
        return Base64.encodeToString(imageBytes, Base64.DEFAULT); // Base64로 인코딩된 문자열 반환
    }
    void showDialog() {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(MenuModActivity.this)
                .setTitle("메뉴 수정")
                .setMessage("메뉴를 수정하시겠습니까?")
                .setPositiveButton("네", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        updateMenu();
                        Toast.makeText(MenuModActivity.this, "메뉴가 수정됐습니다.", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(MenuModActivity.this, UserActivity.class);
//                        startActivity(intent);
                        finish();
                    }
                })

                .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MenuModActivity.this, "메뉴를 수정하지 않았습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }

    private void getItems() {
        String url = "http://192.168.0.25:8081/api/menus?store_idx=" + storeIdx;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        menuList.clear();
                        menuNames.clear();
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject menuObject = response.getJSONObject(i);
                            HashMap<String, Object> menuItem = new HashMap<>();
                            menuItem.put("menu_idx", menuObject.getInt("menu_idx"));
                            menuItem.put("menuName", menuObject.getString("menuName"));
                            // Add other menu properties as needed
                            menuList.add(menuItem);

                            menuNames.add(menuObject.getString("menuName"));
                        }
                        adapter.notifyDataSetChanged(); // Notify adapter about data changes
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(this, "Failed to fetch menu items.", Toast.LENGTH_SHORT).show()
        );
        requestQueue.add(jsonArrayRequest);
    }


    // 메뉴 수정 메서드
    public void updateMenu() {
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        String url = "http://192.168.0.25:8081/api/updateMenu"; // 메뉴 수정 API 엔드포인트
        StringRequest request = new StringRequest(Request.Method.PATCH, url, // 메서드를 PATCH로 변경
                response -> {
                    // 응답 처리 로직
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        // 수정 성공 후 필요한 작업 수행
                        Toast.makeText(MenuModActivity.this, "메뉴가 수정되었습니다.", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    // 에러 처리 로직
                    Toast.makeText(MenuModActivity.this, "메뉴 수정 실패: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
        ) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject jsonBody = new JSONObject();
                try {
                    spNowMenu = findViewById(R.id.spNowMenu);
                    etModMenu = findViewById(R.id.etModMenu);
                    etModPrice = findViewById(R.id.etModPrice);
                    etModExplain = findViewById(R.id.etModExplain);


                    int selectedMenuIndex = spNowMenu.getSelectedItemPosition();
                    HashMap<String, Object> selectedMenu = menuList.get(selectedMenuIndex);
                    int menuIdx = (Integer) selectedMenu.get("menu_idx");
                    jsonBody.put("menu_idx", menuIdx); // 현재 선택된 메뉴의 ID를 JSON에 추가
                    jsonBody.put("menuName", etModMenu.getText().toString());
                    jsonBody.put("menuPrice", etModPrice.getText().toString());
                    jsonBody.put("menuDesc", etModExplain.getText().toString());
                    jsonBody.put("menuImg", encodedImage);
                    jsonBody.put("imgUrl", "테스트용 이미지 경로2"); // imgUrl 로 바꿀 것
                    jsonBody.put("store_idx", storeIdx);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return jsonBody.toString().getBytes();
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