package com.project.project3.adapterViewholder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.project3.R;
import com.project.project3.controller.user.MapFragment;
import com.project.project3.controller.user.UserSearchFragment;
import com.project.project3.model.SearchVO;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {

    private ArrayList<SearchVO> dataset;


    public SearchAdapter(ArrayList<SearchVO> dataset) { this.dataset = dataset; }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list, parent, false);

        SearchViewHolder holder = new SearchViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        SearchVO item = dataset.get(position);
        holder.getImg_list().setImageResource(item.getImg_list());
        holder.getTv_add().setText(item.getTv_add());
        holder.getTv_Name().setText(item.getTv_name());
//        holder.getTv_review().setText(item.getTv_review());
        // holder.getTv_score().setText(item.getTv_score());
        holder.listener = new OnItemClickListener() {
            @Override
            public void OnClickListener(View v, int position) {

                // 여기서 검색프래그먼트 -> 지도프래그먼트로 이동하는데 데이터 하나 가져가야함
                Intent intent = new Intent(holder.itemView.getContext(), MapFragment.class);
                holder.itemView.getContext().startActivity(intent);
            }
        };

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    // 데이터 세트 업데이트 메소드
    public void updateData(ArrayList<SearchVO> newData) {
        dataset = newData;
        notifyDataSetChanged();
    }
}
