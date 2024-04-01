package com.project.project3.adapterViewholder;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.project3.R;
import com.project.project3.controller.advertiser.MainActivity;
import com.project.project3.controller.advertiser.StandingActivity1;
import com.project.project3.controller.advertiser.UserActivity;
import com.project.project3.model.UserCouponVO;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CouponAdapter extends RecyclerView.Adapter<UserCouponViewHolder> {

    private ArrayList<UserCouponVO> dataset;

    public CouponAdapter(ArrayList<UserCouponVO> dataset) {
        this.dataset = dataset;
    }

    private RequestQueue requestQueue;
    private Context context;
    private OnCouponUseListener listener;
    private String clientId;
    private int storeIdx;

    @NonNull
    @Override
    public UserCouponViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_coupon, parent, false);


        UserCouponViewHolder holder = new UserCouponViewHolder(view);
        return holder;
    }

    public interface OnCouponUseListener {
        void onCouponUse(int storeIdx, String clientId, String discountPrice);
    }

    public CouponAdapter(Context context, ArrayList<UserCouponVO> dataset, String clientId, OnCouponUseListener listener) {
        this.context = context;
        this.dataset = dataset;
        this.listener = listener;
        this.clientId = clientId;
        if (this.context != null) { // Context 유효성 검사
            this.requestQueue = Volley.newRequestQueue(this.context);
        } else {
            Log.e("CouponAdapter", "Context is null in CouponAdapter constructor.");
        }

    }

    private String storeName;
    private String discountPrice;


    @Override
    public void onBindViewHolder(@NonNull UserCouponViewHolder holder, int position) {


        UserCouponVO item = dataset.get(position);

        holder.getCouponName().setText(item.getUserCouponStoreName());
        holder.getCouponPrice().setText(item.getUserCouponPrice());
        holder.getCouponDate().setText(item.getUserCouponDate());
        holder.getCouponImg().setImageResource(item.getUserCouponImg());
        holder.listener = new OnItemClickListener() {
            @Override
            public void OnClickListener(View v, int position) {
                showDialog(v.getContext(), position);

            }
        };
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }



    void showDialog(Context context, int position) {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(context)
                .setTitle("직원확인 처리를 하시겠습니까?")
                .setMessage("직원 확인 후에는 쿠폰은 사용처리되어 더 이상 쓸 수 없습니다.")
                .setPositiveButton("네", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        UserCouponVO item = dataset.get(position);
                        storeName = item.getUserCouponStoreName();
                        discountPrice = item.getUserCouponPrice();

                        // 가게 선택자 조회 메서드
//                        getStoreIdx(storeName); context가 null값이라 통신이 안됌...

                        // 쿠폰 사용 메서드
//                        useCoupon(storeIdx, discountPrice,clientId);
                        dataset.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(context, "쿠폰 사용 완료", Toast.LENGTH_SHORT).show();

                    }
                })

                .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "쿠폰 사용 취소", Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }


    public void getStoreIdx(String storeName) {

        requestQueue = Volley.newRequestQueue(context);

        String url = "http://192.168.219.191:8081/api/getStoreIdx";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        // 서버로부터 응답을 받았을 때
                        JSONObject jsonResponse = new JSONObject(response);
                         storeIdx = jsonResponse.getInt("storeIdx");
                        Log.d("가게선택자", String.valueOf(storeIdx));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    // 에러가 발생했을 때
                }
        ) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                // 요청 본문 구성
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("storeName", storeName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return jsonBody.toString().getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // 요청 헤더 설정
                Map<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);
    }


    public void useCoupon(int storeIdx, String discountPrice, String clientId){
        String url = "http://192.168.219.101:8081/api/useCoupon"; // 서버의 적절한 엔드포인트로 수정하세요

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("storeIdx", storeIdx);
            jsonBody.put("discountPrice", discountPrice);
            jsonBody.put("clientId", clientId);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                    response -> {
                        // 성공적으로 쿠폰 사용 처리됐을 때의 로직
                        Log.d("UseCoupon", "쿠폰 사용 완료!!!");
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


}
