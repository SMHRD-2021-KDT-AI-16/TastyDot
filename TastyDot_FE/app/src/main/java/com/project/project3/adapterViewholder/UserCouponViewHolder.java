package com.project.project3.adapterViewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.project3.R;

public class UserCouponViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    OnItemClickListener listener;
    private TextView couponName;
    private TextView couponPrice;
    private TextView couponDate;
    private ImageView couponImg;

    public UserCouponViewHolder(@NonNull View itemView) {
        super(itemView);
        this.couponName = itemView.findViewById(R.id.tvCouponStore);
        this.couponPrice = itemView.findViewById(R.id.tvUserCouponMoney);
        this.couponDate = itemView.findViewById(R.id.tvCouponDate);
        this.couponImg = itemView.findViewById(R.id.imgUserCoupon);
        itemView.setOnClickListener(this);
        // itemview를 클릭하는 이벤트
    }

    public TextView getCouponName() {
        return couponName;
    }

    public TextView getCouponPrice() {
        return couponPrice;
    }

    public TextView getCouponDate() {
        return couponDate;
    }

    public ImageView getCouponImg() {
        return couponImg;
    }



    @Override
    public void onClick(View v) {
        this.listener.OnClickListener(v,getBindingAdapterPosition());
    }
}
