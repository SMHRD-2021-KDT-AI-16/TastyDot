package com.project.project3.adapterViewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.project3.R;
import com.project.project3.model.CouponVO;

import java.util.ArrayList;
import java.util.List;

public class HistoryCouponAdapter extends RecyclerView.Adapter<HistoryCouponAdapter.ViewHolder> {

    private List<CouponVO> couponList;

    public HistoryCouponAdapter() {
        this.couponList = new ArrayList<>();
    }
    /*public HistoryCouponAdapter(List<CouponVO> couponList) {
        this.couponList = couponList;
    }
    데이터 입력되면 이거 사용
    */

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coupon_hitory, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CouponVO coupon = couponList.get(position);
        holder.nameCoupon.setText(coupon.getCouponName());
        holder.usedCoupon.setText(coupon.getCouponUsed());
        holder.createCoupon.setText(coupon.getCouponCreate());
    }

    @Override
    public int getItemCount() {
        return couponList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameCoupon;
        TextView usedCoupon;
        TextView createCoupon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameCoupon = itemView.findViewById(R.id.nameCoupon);
            usedCoupon = itemView.findViewById(R.id.usedCoupon);
            createCoupon = itemView.findViewById(R.id.createCoupon);
        }
    }
}
