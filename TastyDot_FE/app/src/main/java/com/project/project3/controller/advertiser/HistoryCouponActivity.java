package com.project.project3.controller.advertiser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.project.project3.R;
import com.project.project3.adapterViewholder.HistoryCouponAdapter;

public class HistoryCouponActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HistoryCouponAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_coupon);


        recyclerView = findViewById(R.id.rvUsedCoupon);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new HistoryCouponAdapter();
        recyclerView.setAdapter(adapter);

    }
}