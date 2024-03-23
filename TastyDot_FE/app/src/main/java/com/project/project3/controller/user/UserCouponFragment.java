package com.project.project3.controller.user;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.project.project3.R;

import com.project.project3.adapterViewholder.CouponAdapter;
import com.project.project3.model.UserCouponVO;

import java.util.ArrayList;

public class UserCouponFragment extends Fragment {
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_user_coupon, container, false);
            RecyclerView rv = view.findViewById(R.id.rvUserCoupon);
            ArrayList<UserCouponVO> vos = new ArrayList<UserCouponVO>();


            UserCouponVO vo = new UserCouponVO("은선식당","1000","20240322",R.drawable.camera);
            UserCouponVO vo2 = new UserCouponVO("세명식당","2000","20240323",R.drawable.camera);
            vos.add(vo);
            vos.add(vo2);
            ArrayList<UserCouponVO> coupons = vos; //  경근씨 DB쿠폰 데이터 입력 ex20240122참고해서 만들면됩니다!
            CouponAdapter adapter = new CouponAdapter(coupons);

            rv.setAdapter(adapter);
            LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
            rv.setLayoutManager(manager);
            // 리사이클러뷰 새로고침 : adpater.notifyDatasetChanged();



            return view;
        }

        void showDialog() {
            AlertDialog.Builder msgBuilder = new AlertDialog.Builder(getActivity())
                    .setTitle("직원확인 처리를 하시겠습니까?")
                    .setMessage("직원 확인 후에는 쿠폰은 사용처리되어 더 이상 쓸 수 없습니다.")
                    .setPositiveButton("네", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getContext(), "쿠폰이 사용됐습니다.", Toast.LENGTH_SHORT).show();

                        }
                    })

                    .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getContext(), "쿠폰 사용 취소.", Toast.LENGTH_SHORT).show();
                        }
                    });
            AlertDialog msgDlg = msgBuilder.create();
            msgDlg.show();
        }
    }
