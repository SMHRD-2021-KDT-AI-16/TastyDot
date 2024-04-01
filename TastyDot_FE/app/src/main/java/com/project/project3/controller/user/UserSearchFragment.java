package com.project.project3.controller.user;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.project3.R;
import com.project.project3.adapterViewholder.SearchAdapter;
import com.project.project3.model.SearchVO;


import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

// 깃허브 푸시안됨ㅅ
public class UserSearchFragment extends Fragment {
    private RecyclerView rv_search;
    private Button kr_01,kr_02, kr_03, kr_04, kr_05, cn_01, cn_02, cn_03, cn_04, cn_05, jp_01, jp_02, jp_03, jp_04, jp_05, ws_01, ws_02, ws_03, ws_04, snack_01, snack_02,snack_03,snack_04,snack_05, cafe_01, cafe_02, cafe_03, cafe_04, cafe_05 ;
    private ArrayList<SearchVO> items = new ArrayList<>();
    private SearchAdapter adapter;
    private OkHttpClient client;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_search, container, false);
        rv_search = view.findViewById(R.id.rv_search);
        // OkHttpClient 인스턴스 초기화
        client = new OkHttpClient.Builder()
                .connectTimeout(300, TimeUnit.SECONDS) // 연결 타임아웃
                .writeTimeout(300, TimeUnit.SECONDS) // 쓰기 타임아웃
                .readTimeout(300, TimeUnit.SECONDS) // 읽기 타임아웃
                .build();


        // 검색 결과를 담을 ArrayList 생성
        ArrayList<SearchVO> items = new ArrayList<>();


        // 검색 결과를 표시할 어댑터 생성 및 설정
        SearchAdapter adapter = new SearchAdapter(items);
        rv_search.setAdapter(adapter);

        // 리사이클러뷰 레이아웃 매니저 설정
        rv_search.setLayoutManager(new LinearLayoutManager(getContext()));

        // 카테고리별 스크롤뷰 초기 설정
        setupCategoryListeners(view);

        // 초기에 모든 스크롤뷰를 숨김
        hideAllScrollViews(view);
        setupView(view);
        setupListeners();



// onclick
        return view;
    }

    // 뷰 초기화
    private void setupView(View view) {
        rv_search = view.findViewById(R.id.rv_search);
        // 검색 결과 RecyclerView 설정
        adapter = new SearchAdapter(items);
        rv_search.setAdapter(adapter);
        rv_search.setLayoutManager(new LinearLayoutManager(getContext()));

        // 카테고리 버튼 초기화
        kr_01 = view.findViewById(R.id.kr_01);
        kr_02 = view.findViewById(R.id.kr_02);
        kr_03 = view.findViewById(R.id.kr_03);
        kr_04 = view.findViewById(R.id.kr_04);
        //kr_05 = view.findViewById(R.id.kr_05);

        cn_01 = view.findViewById(R.id.cn_01);
        cn_02 = view.findViewById(R.id.cn_02);
        cn_03 = view.findViewById(R.id.cn_03);
        cn_04 = view.findViewById(R.id.cn_04);
        cn_05 = view.findViewById(R.id.cn_05);

        jp_01 = view.findViewById(R.id.jp_01);
        jp_02 = view.findViewById(R.id.jp_02);
        jp_03 = view.findViewById(R.id.jp_03);
        jp_04 = view.findViewById(R.id.jp_04);
        jp_05 = view.findViewById(R.id.jp_05);

        ws_01 = view.findViewById(R.id.ws_01);
        ws_02 = view.findViewById(R.id.ws_02);
        ws_03 = view.findViewById(R.id.ws_03);
        ws_04 = view.findViewById(R.id.ws_04);

        snack_01 = view.findViewById(R.id.snack_01);
        snack_02 = view.findViewById(R.id.snack_02);
        snack_03 = view.findViewById(R.id.snack_03);
        snack_04 = view.findViewById(R.id.snack_04);
        snack_05 = view.findViewById(R.id.snack_05);

        cafe_01 = view.findViewById(R.id.cafe_01);
        cafe_02 = view.findViewById(R.id.cafe_02);
        cafe_03 = view.findViewById(R.id.cafe_03);
        cafe_04 = view.findViewById(R.id.cafe_04);
        cafe_05 = view.findViewById(R.id.cafe_05);
    }

    // 리스너 설정
    private void setupListeners() {
        kr_01.setOnClickListener(v -> loadDataFromJson("설렁탕"));
        kr_02.setOnClickListener(v -> loadDataFromJson("국밥"));
        kr_03.setOnClickListener(v -> loadDataFromJson("회"));
        kr_04.setOnClickListener(v -> loadDataFromJson("한정식"));

        cn_01.setOnClickListener(v -> loadDataFromJson2("짜장면/짬뽕"));
        cn_02.setOnClickListener(v -> loadDataFromJson2("볶음밥"));
        cn_03.setOnClickListener(v -> loadDataFromJson2("탕수육"));

        jp_01.setOnClickListener(v -> loadDataFromJson3("초밥"));
        jp_02.setOnClickListener(v -> loadDataFromJson3("돈까스"));
        jp_03.setOnClickListener(v -> loadDataFromJson3("롤"));

        ws_01.setOnClickListener(v -> loadDataFromJson4("파스타"));
        ws_02.setOnClickListener(v -> loadDataFromJson4("피자"));
        ws_03.setOnClickListener(v -> loadDataFromJson4("햄버거"));

        snack_01.setOnClickListener(v -> loadDataFromJson5("떡볶이"));
        snack_02.setOnClickListener(v -> loadDataFromJson5("라면"));
        snack_03.setOnClickListener(v -> loadDataFromJson5("쫄면"));
        snack_03.setOnClickListener(v -> loadDataFromJson5("튀김"));

        cafe_01.setOnClickListener(v -> loadDataFromJson6("아메리카노"));
        cafe_02.setOnClickListener(v -> loadDataFromJson6("카페라떼"));
        cafe_03.setOnClickListener(v -> loadDataFromJson6("딸기라떼"));
        cafe_04.setOnClickListener(v -> loadDataFromJson6("카라멜마키아또"));


    }

    private void loadDataFromJson(String category) {
        try {
            // assets 폴더에서 JSON 파일 읽기
            InputStream is = getActivity().getAssets().open("food_01.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");

            JSONObject obj = new JSONObject(json);
            JSONArray 한식Array = obj.getJSONArray("한식");
            for (int i = 0; i < 한식Array.length(); i++) {
                JSONObject categoryObj = 한식Array.getJSONObject(i);
                if (categoryObj.has(category)) {
                    JSONArray categoryArray = categoryObj.getJSONArray(category);
                    items.clear();
                    for (int j = 0; j < categoryArray.length(); j++) {
                        JSONObject categoryItem = categoryArray.getJSONObject(j);
                        items.add(new SearchVO(
                                categoryItem.getString("storeName"),
                                categoryItem.getString("address"),
                                categoryItem.getString("menu"),
                                categoryItem.getString("hashtag")
                        ));
                    }
                    adapter.notifyDataSetChanged();
                    break;
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadDataFromJson2(String category) {
        try {
            // assets 폴더에서 JSON 파일 읽기
            InputStream is = getActivity().getAssets().open("food_02.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");

            JSONObject obj = new JSONObject(json);
            JSONArray 중식Array = obj.getJSONArray("중식");
            for (int i = 0; i < 중식Array.length(); i++) {
                JSONObject categoryObj = 중식Array.getJSONObject(i);
                if (categoryObj.has(category)) {
                    JSONArray categoryArray = categoryObj.getJSONArray(category);
                    items.clear();
                    for (int j = 0; j < categoryArray.length(); j++) {
                        JSONObject categoryItem = categoryArray.getJSONObject(j);
                        items.add(new SearchVO(
                                categoryItem.getString("storeName"),
                                categoryItem.getString("address"),
                                categoryItem.getString("menu"),
                                categoryItem.getString("hashtag")
                        ));
                    }
                    adapter.notifyDataSetChanged();
                    break;
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadDataFromJson3(String category) {
        try {
            // assets 폴더에서 JSON 파일 읽기
            InputStream is = getActivity().getAssets().open("food_03.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");

            JSONObject obj = new JSONObject(json);
            JSONArray 일식Array = obj.getJSONArray("일식");
            for (int i = 0; i < 일식Array.length(); i++) {
                JSONObject categoryObj = 일식Array.getJSONObject(i);
                if (categoryObj.has(category)) {
                    JSONArray categoryArray = categoryObj.getJSONArray(category);
                    items.clear();
                    for (int j = 0; j < categoryArray.length(); j++) {
                        JSONObject categoryItem = categoryArray.getJSONObject(j);
                        items.add(new SearchVO(
                                categoryItem.getString("storeName"),
                                categoryItem.getString("address"),
                                categoryItem.getString("menu"),
                                categoryItem.getString("hashtag")
                        ));
                    }
                    adapter.notifyDataSetChanged();
                    break;
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadDataFromJson4(String category) {
        try {
            // assets 폴더에서 JSON 파일 읽기
            InputStream is = getActivity().getAssets().open("food_04.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");

            JSONObject obj = new JSONObject(json);
            JSONArray 양식Array = obj.getJSONArray("양식");
            for (int i = 0; i < 양식Array.length(); i++) {
                JSONObject categoryObj = 양식Array.getJSONObject(i);
                if (categoryObj.has(category)) {
                    JSONArray categoryArray = categoryObj.getJSONArray(category);
                    items.clear();
                    for (int j = 0; j < categoryArray.length(); j++) {
                        JSONObject categoryItem = categoryArray.getJSONObject(j);
                        items.add(new SearchVO(
                                categoryItem.getString("storeName"),
                                categoryItem.getString("address"),
                                categoryItem.getString("menu"),
                                categoryItem.getString("hashtag")
                        ));
                    }
                    adapter.notifyDataSetChanged();
                    break;
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadDataFromJson5(String category) {
        try {
            // assets 폴더에서 JSON 파일 읽기
            InputStream is = getActivity().getAssets().open("food_05.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");

            JSONObject obj = new JSONObject(json);
            JSONArray 분식Array = obj.getJSONArray("분식");
            for (int i = 0; i < 분식Array.length(); i++) {
                JSONObject categoryObj = 분식Array.getJSONObject(i);
                if (categoryObj.has(category)) {
                    JSONArray categoryArray = categoryObj.getJSONArray(category);
                    items.clear();
                    for (int j = 0; j < categoryArray.length(); j++) {
                        JSONObject categoryItem = categoryArray.getJSONObject(j);
                        items.add(new SearchVO(
                                categoryItem.getString("storeName"),
                                categoryItem.getString("address"),
                                categoryItem.getString("menu"),
                                categoryItem.getString("hashtag")
                        ));
                    }
                    adapter.notifyDataSetChanged();
                    break;
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadDataFromJson6(String category) {
        try {
            // assets 폴더에서 JSON 파일 읽기
            InputStream is = getActivity().getAssets().open("food_06.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");

            JSONObject obj = new JSONObject(json);
            JSONArray 카페Array = obj.getJSONArray("카페");
            for (int i = 0; i < 카페Array.length(); i++) {
                JSONObject categoryObj = 카페Array.getJSONObject(i);
                if (categoryObj.has(category)) {
                    JSONArray categoryArray = categoryObj.getJSONArray(category);
                    items.clear();
                    for (int j = 0; j < categoryArray.length(); j++) {
                        JSONObject categoryItem = categoryArray.getJSONObject(j);
                        items.add(new SearchVO(
                                categoryItem.getString("storeName"),
                                categoryItem.getString("address"),
                                categoryItem.getString("menu"),
                                categoryItem.getString("hashtag")
                        ));
                    }
                    adapter.notifyDataSetChanged();
                    break;
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private String getCategoryPath(String category) {
        switch (category) {
            case "한식":
                return "korean";
            case "중식":
                return "cn";
            case "일식":
                return "jp";
            case "양식":
                return "ws";
            case "분식":
                return "snack";
            case "카페":
                return "cafe";
            default:
                return ""; // 기본값 처리
        }
    }




    // 모든 스크롤뷰를 숨기는 메소드
    private void hideAllScrollViews(View view) {
        final HorizontalScrollView scrollView = view.findViewById(R.id.horizontalScrollView);
        final HorizontalScrollView scrollView2 = view.findViewById(R.id.horizontalScrollView2);
        final HorizontalScrollView scrollView3 = view.findViewById(R.id.horizontalScrollView3);
        final HorizontalScrollView scrollView4 = view.findViewById(R.id.horizontalScrollView4);
        final HorizontalScrollView scrollView5 = view.findViewById(R.id.horizontalScrollView5);
        final HorizontalScrollView scrollView6 = view.findViewById(R.id.horizontalScrollView6);

        scrollView.setVisibility(View.GONE);
        scrollView2.setVisibility(View.GONE);
        scrollView3.setVisibility(View.GONE);
        scrollView4.setVisibility(View.GONE);
        scrollView5.setVisibility(View.GONE);
        scrollView6.setVisibility(View.GONE);
    }

    // 카테고리별 스크롤뷰 리스너 설정 메소드
    private void setupCategoryListeners(View view) {
        TextView korean = view.findViewById(R.id.korean);
        final HorizontalScrollView scrollView = view.findViewById(R.id.horizontalScrollView);
        korean.setOnClickListener(v -> toggleScrollViewVisibility(view, scrollView));

        TextView chinese = view.findViewById(R.id.chinese);
        final HorizontalScrollView scrollView2 = view.findViewById(R.id.horizontalScrollView2);
        chinese.setOnClickListener(v -> toggleScrollViewVisibility(view, scrollView2));

        TextView japanese = view.findViewById(R.id.japanese);
        final HorizontalScrollView scrollView3 = view.findViewById(R.id.horizontalScrollView3);
        japanese.setOnClickListener(v -> toggleScrollViewVisibility(view, scrollView3));

        TextView western = view.findViewById(R.id.western);
        final HorizontalScrollView scrollView4 = view.findViewById(R.id.horizontalScrollView4);
        western.setOnClickListener(v -> toggleScrollViewVisibility(view, scrollView4));

        TextView snack = view.findViewById(R.id.snack);
        final HorizontalScrollView scrollView5 = view.findViewById(R.id.horizontalScrollView5);
        snack.setOnClickListener(v -> toggleScrollViewVisibility(view, scrollView5));

        TextView cafe = view.findViewById(R.id.cafe);
        final HorizontalScrollView scrollView6 = view.findViewById(R.id.horizontalScrollView6);
        cafe.setOnClickListener(v -> toggleScrollViewVisibility(view, scrollView6));
    }

    // 스크롤뷰 가시성을 토글하는 메소드
    private void toggleScrollViewVisibility(View parentView, HorizontalScrollView scrollView) {
        hideAllScrollViews(parentView); // 먼저 모든 스크롤뷰를 숨김
        if (scrollView.getVisibility() == View.VISIBLE) {
            scrollView.setVisibility(View.GONE);
        } else {
            scrollView.setVisibility(View.VISIBLE);
        }
    }

    private String decodeUnicode(String unicodeStr) { // 유니코드 문자열을 해독하는 메서드 정의
        Pattern pattern = Pattern.compile("\\\\u([0-9a-f]{4})", Pattern.CASE_INSENSITIVE);
        // 유니코드 패턴을 찾기 위한 정규표현식 패턴 생성

        Matcher matcher = pattern.matcher(unicodeStr);
        // 주어진 문자열에서 패턴 매칭을 수행할 Matcher 객체 생성

        StringBuffer buffer = new StringBuffer(unicodeStr.length());
        // 문자열을 담을 StringBuffer 생성
        while (matcher.find()) { // 패턴 매칭이 있을 때까지 반복
            matcher.appendReplacement(buffer, String.valueOf((char) Integer.parseInt(matcher.group(1), 16)));
            // 매칭된 유니코드를 해당하는 문자로 변환하여 StringBuffer에 추가
        }
        matcher.appendTail(buffer);
        // 나머지 문자열을 StringBuffer에 추가
        return buffer.toString();
        // StringBuffer를 문자열로 변환하여 반환
    }

}
