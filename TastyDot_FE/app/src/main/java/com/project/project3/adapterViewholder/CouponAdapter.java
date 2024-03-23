package com.project.project3.adapterViewholder;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.project.project3.R;
import com.project.project3.model.UserCouponVO;

import java.util.ArrayList;

public class CouponAdapter extends RecyclerView.Adapter<UserCouponViewHolder> {

    private ArrayList<UserCouponVO> dataset;
    public CouponAdapter(ArrayList<UserCouponVO> dataset){
        this.dataset = dataset;
    }

    @NonNull
    @Override
    public UserCouponViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_coupon, parent, false);

        UserCouponViewHolder holder = new UserCouponViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserCouponViewHolder holder, int position) {
        UserCouponVO item = dataset.get(position);
        holder.getCouponName().setText(item.getUserCouponName());
        holder.getCouponPrice().setText(item.getUserCouponPrice());
        holder.getCouponDate().setText(item.getUserCouponDate());
        holder.getCouponImg().setImageResource(item.getUserCouponImg());
        holder.listener = new OnItemClickListener() {
            @Override
            public void OnClickListener(View v, int position) {
                showDialog(v.getContext(),position);

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

}
