package com.project.project3.controller.user;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.project.project3.R;

import java.util.ArrayList;

public class UserSearchFragment extends Fragment {

    // 모델 클래스 정의
    public static class ListItem {
        private String name;
        private String address;
        private double score;
        private int reviewCount;

        public ListItem(String name, String address, double score, int reviewCount) {
            this.name = name;
            this.address = address;
            this.score = score;
            this.reviewCount = reviewCount;
        }

        public String getName() {
            return name;
        }

        public String getAddress() {
            return address;
        }

        public double getScore() {
            return score;
        }

        public int getReviewCount() {
            return reviewCount;
        }
    }

    // 어댑터 클래스 정의
    public static class ListAdapter extends ArrayAdapter<ListItem> {
        public ListAdapter(Context context, ArrayList<ListItem> items) {
            super(context, 0, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // convertView가 재활용될 수 있도록 설정
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list, parent, false);
            }

            // 해당 position에 있는 데이터 가져오기
            ListItem item = getItem(position);

            // 리스트 아이템에 데이터 설정
            TextView nameTextView = convertView.findViewById(R.id.tv_Name);
            TextView addressTextView = convertView.findViewById(R.id.tv_add);
            TextView scoreTextView = convertView.findViewById(R.id.tv_score);
            TextView reviewCountTextView = convertView.findViewById(R.id.tv_review);

            nameTextView.setText(item.getName());
            addressTextView.setText(item.getAddress());
            scoreTextView.setText(String.valueOf(item.getScore()));
            reviewCountTextView.setText(String.valueOf(item.getReviewCount()));

            return convertView;
        }
    }

    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_search, container, false);
        listView = view.findViewById(R.id.listview1);

        // 리스트 아이템 데이터 생성
        ArrayList<ListItem> items = new ArrayList<>();

        // 필요한 만큼 데이터를 추가할 수 있습니다.
        // 아래는 예시
        items.add(new ListItem("솔마을 과자점", "전남 목포시", 4.9, 500));
        items.add(new ListItem("솔마을 과자점1", "전남 목포시1", 4.8, 499));
        items.add(new ListItem("솔마을 과자점2", "전남 목포시2", 4.7, 498));
        items.add(new ListItem("솔마을 과자점3", "전남 목포시3", 4.6, 497));
        items.add(new ListItem("솔마을 과자점4", "전남 목포시4", 4.5, 496));
        items.add(new ListItem("솔마을 과자점", "전남 목포시", 4.9, 500));
        items.add(new ListItem("솔마을 과자점1", "전남 목포시1", 4.8, 499));
        items.add(new ListItem("솔마을 과자점2", "전남 목포시2", 4.7, 498));
        items.add(new ListItem("솔마을 과자점3", "전남 목포시3", 4.6, 497));
        items.add(new ListItem("솔마을 과자점4", "전남 목포시4", 4.5, 496));
        items.add(new ListItem("솔마을 과자점", "전남 목포시", 4.9, 500));
        items.add(new ListItem("솔마을 과자점1", "전남 목포시1", 4.8, 499));
        items.add(new ListItem("솔마을 과자점2", "전남 목포시2", 4.7, 498));
        items.add(new ListItem("솔마을 과자점3", "전남 목포시3", 4.6, 497));
        items.add(new ListItem("솔마을 과자점4", "전남 목포시4", 4.5, 496));

        // 어댑터 생성 및 리스트뷰에 설정
        ListAdapter adapter = new ListAdapter(getContext(), items);
        listView.setAdapter(adapter);

        return view;
    }
}
