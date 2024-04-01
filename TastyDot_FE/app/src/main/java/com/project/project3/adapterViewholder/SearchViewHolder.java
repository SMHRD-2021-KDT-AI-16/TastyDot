package com.project.project3.adapterViewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.project3.R;

public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    OnItemClickListener listener;

    private TextView tv_Name;

    private TextView tv_add;

    private TextView tv_score;

    private TextView tv_review;
    private TextView tv_hash;
    private TextView tv_rate;

    private ImageView img_list;

    public TextView getTv_Name() {
        return tv_Name;
    }
    public TextView getTv_rate(){ return tv_rate;}

    public TextView getTv_add() {
        return tv_add;
    }

    public TextView getTv_score() {
        return tv_score;
    }

    public TextView getTv_hash() {
        return tv_hash;
    }

    public ImageView getImg_list() {
        return img_list;
    }

    public SearchViewHolder(@NonNull View itemView) {
        super(itemView);
        this.tv_Name = itemView.findViewById(R.id.tv_Name);
        this.tv_add = itemView.findViewById(R.id.tv_add);
        this.tv_score = itemView.findViewById(R.id.tv_score);
        this.img_list = itemView.findViewById(R.id.img_list);
        this.tv_hash = itemView.findViewById(R.id.tv_hash);

    itemView.setOnClickListener(this);
        // itemview를 클릭하는 이벤트
        }




@Override
public void onClick(View v) {
        this.listener.OnClickListener(v,getBindingAdapterPosition());
        }
}
