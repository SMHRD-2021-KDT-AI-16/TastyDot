package com.project.project3.controller.user;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.project.project3.R;

import com.project.project3.adapterViewholder.CouponAdapter;
import com.project.project3.model.UserCouponVO;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UserCouponFragment extends Fragment implements CouponAdapter.OnCouponUseListener{
    String id;
    String clientId;
    String clientName;
    String clientEmail;
    String discountPrice;
    String storeName;
    int storeIdx;

    private RequestQueue requestQueue;
    RecyclerView rvUserCoupons;
    private ArrayList<UserCouponVO> userCoupons = new ArrayList<>();

    public UserCouponFragment() {
        // 필수 공개 생성자
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_coupon, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            id = getArguments().getString("id");
            clientId = getArguments().getString("clientId");
            clientName = getArguments().getString("clientName");
            clientEmail = getArguments().getString("clientEmail");
            Log.d("쿠폰 프레그먼트로 넘어왔는지? ", "Client Name: " + String.valueOf(clientName));
        }

        rvUserCoupons = view.findViewById(R.id.rvUserCoupon);
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getContext());
        }
        CouponAdapter adapter = new CouponAdapter(getContext(), userCoupons, clientId, (CouponAdapter.OnCouponUseListener) this);
        rvUserCoupons.setAdapter(adapter);
        loadCoupons();
        return view;
    }

    public void useCoupon(int storeIdx, String clientId, String discountPrice) {

        String url = "http://192.168.219.101:8081/api/useCoupon"; // 서버의 적절한 엔드포인트로 수정하세요

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("storeIdx", storeIdx);
            jsonBody.put("clientId", clientId);
            jsonBody.put("discountPrice", discountPrice);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                    response -> {
                        // 성공적으로 쿠폰 사용 처리됐을 때의 로직
                        Log.d("UseCoupon", "Successfully used coupon.");
                        // 사용자에게 성공 메시지 표시 등
                    },
                    error -> {
                        // 요청 실패 시 처리
                        Log.e("UseCoupon", "Error: " + error.toString());
                        // 사용자에게 실패 메시지 표시 등
                    });

            requestQueue.add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void loadCoupons() {
        String url = "http://192.168.219.101:8081/api/searchUserCoupons"; // 올바른 URL로 수정하세요
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        Log.d("LoadCoupons", "Response: " + response.toString());
                        ArrayList<UserCouponVO> userCoupons = new ArrayList<>();
                        Log.d("응답길이", String.valueOf(response.length()));
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject couponObject = response.getJSONObject(i);
                            storeIdx = couponObject.optInt("storeIdx", 1);
                            storeName = couponObject.optString("storeName", "가게 이름 없음");
                            discountPrice = couponObject.getString("discountPrice");
                            String issuedDateString = couponObject.optString("lastIssuedDate", "");

                            SimpleDateFormat serverDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                            Date issuedDate = null;
                            try {
                                if (!issuedDateString.isEmpty()) {
                                    issuedDate = serverDateFormat.parse(issuedDateString);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            Calendar cal = Calendar.getInstance();
                            if (issuedDate != null) {
                                cal.setTime(issuedDate);
                                cal.add(Calendar.MONTH, 3);
                            }

                            SimpleDateFormat displayDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                            String validUntil = issuedDateString.isEmpty() ? "날짜 정보 없음" : displayDateFormat.format(cal.getTime());

                            Log.d("CouponInfo", "Coupon: " + couponObject.toString() + ", Valid Until: " + validUntil);

                            // 생성자 호출 시 `userCouponName`을 `storeName`으로 변경
                            userCoupons.add(new UserCouponVO(storeName, discountPrice, validUntil, R.drawable.coupon));
                        }
                        CouponAdapter adapter = new CouponAdapter(userCoupons);
                        rvUserCoupons.setAdapter(adapter);
                        rvUserCoupons.setLayoutManager(new LinearLayoutManager(getContext()));
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("LoadCoupons", "Error processing response", e);
                    }
                },
                error -> {
                    Log.e("LoadCoupons", "Request error: " + error.toString());
                });

        requestQueue.add(jsonArrayRequest);
    }


    @Override
    public void onCouponUse(int storeIdx, String clientId, String discountPrice) {

    }
}
