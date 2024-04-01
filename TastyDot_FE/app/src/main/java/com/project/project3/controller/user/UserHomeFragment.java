package com.project.project3.controller.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.project3.R;
import com.project.project3.adapter.HomeSlideAdapter;

import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator3;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class UserHomeFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ViewPager2 mPager;
    private FragmentStateAdapter pagerAdapter;
    private int num_page = 4;
    private CircleIndicator3 mIndicator;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    // Delay time in milliseconds
    private static final long DELAY_MS = 4000;
    // Time between sliding in milliseconds
    private static final long PERIOD_MS = 4000;

    private Timer timer;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable = new Runnable() {
        public void run() {
            if (mPager.getCurrentItem() < num_page - 1) {
                mPager.setCurrentItem(mPager.getCurrentItem() + 1);
            } else {
                mPager.setCurrentItem(0);
            }
        }
    };

    public UserHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserHomeFragment newInstance(String param1, String param2) {
        UserHomeFragment fragment = new UserHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_home, container, false);

        // Initialize ViewPager2
        mPager = view.findViewById(R.id.viewpager);
        pagerAdapter = new HomeSlideAdapter(requireActivity(), num_page); // MyAdapter는 사용자가 직접 작성한 것으로 대체해야 합니다.
        mPager.setAdapter(pagerAdapter);

        // Initialize CircleIndicator3
        mIndicator = view.findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        mIndicator.createIndicators(num_page, 0);

        // Start auto sliding
        startAutoSlide();

        return view;
    }
    public void onDestroyView() {
        super.onDestroyView();
        // Stop auto sliding when fragment is destroyed
        stopAutoSlide();
    }
    private void startAutoSlide() {
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(runnable);
                }
            }, DELAY_MS, PERIOD_MS);
        }
    }

    private void stopAutoSlide() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}