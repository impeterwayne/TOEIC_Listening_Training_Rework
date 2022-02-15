package com.example.a900toeic.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.a900toeic.Fragment.AccountFragment;
import com.example.a900toeic.Fragment.PracticeFragment;
import com.example.a900toeic.Fragment.RealTestFragment;
import com.example.a900toeic.Fragment.ReviewFragment;
import com.example.a900toeic.Fragment.StatisticFragment;

public class MainViewPagerAdapter extends FragmentStateAdapter {
    public MainViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1: return new StatisticFragment();
            case 2: return new RealTestFragment();
            case 3: return new ReviewFragment();
            case 4: return new AccountFragment();
            default: return new PracticeFragment();
        }

    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
