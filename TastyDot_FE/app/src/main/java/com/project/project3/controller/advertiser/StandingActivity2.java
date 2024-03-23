package com.project.project3.controller.advertiser; // 패키지 선언 추가

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project.project3.R;

public class StandingActivity2 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standing2);


        // 다음 버튼 클릭 이벤트 처리
        findViewById(R.id.btn_next2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다음 화면으로 이동
                Intent intent = new Intent(StandingActivity2.this, StandingActivity3.class);
                startActivity(intent);
            }
        });

        // 나가기 버튼 클릭 이벤트 처리
        findViewById(R.id.btn_quit1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StandingActivity2.this, StandingActivity1.class);
                startActivity(intent);
            }
        });

        // Spinner를 레이아웃과 연결
        Spinner spinner = findViewById(R.id.tv_spinner);

        // Spinner에 표시할 옵션을 정의
        String[] spinnerOptions = getResources().getStringArray(R.array.spinner_options);

        // ArrayAdapter 생성 및 설정
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Spinner에 adapter 설정
        spinner.setAdapter(adapter);

        // Spinner 아이템 선택 이벤트 처리
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 선택된 항목의 텍스트 가져오기
                String selectedItem = parent.getItemAtPosition(position).toString();
                // 토스트 메시지로 선택된 항목 출력
                Toast.makeText(StandingActivity2.this, selectedItem + " 선택됨", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 아무것도 선택되지 않았을 때 처리할 작업
            }
        });
    }

    // Retrofit Interface를 사용하여 API 호출 메서드를 정의
    private void getAddressList(String serviceKey, String searchWord, int countPerPage, int currentPage) {
        // 여기에 Retrofit을 사용하여 API를 호출하는 코드를 작성하세요.
        // 호출 결과를 처리하는 부분은 onResponse() 메서드와 onFailure() 메서드에 작성합니다.
        // API 호출 결과를 웹뷰에 표시할 수 있도록 수정하세요.
    }
}
