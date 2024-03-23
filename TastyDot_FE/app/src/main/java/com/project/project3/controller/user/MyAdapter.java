package com.project.project3.controller.user;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyAdapter extends FragmentStateAdapter {

    private int num_pages;

    public MyAdapter(@NonNull FragmentActivity fragmentActivity, int num_pages) {
        super(fragmentActivity);
        this.num_pages = num_pages;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return a different fragment based on the position
        switch (position) {
            case 0:
                return new Frame1Fragment(); // Replace with your first fragment class name
            case 1:
                return new Frame2Fragment(); // Replace with your second fragment class name
            case 2:
                return new Frame3Fragment(); // Replace with your third fragment class name
            case 3:
                return new Frame4Fragment(); // Replace with your fourth fragment class name
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return num_pages;
    }
}
