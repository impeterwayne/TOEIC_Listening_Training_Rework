package com.peterwayne.toeic900.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.peterwayne.toeic900.Fragment.ProfileFragment;
import com.peterwayne.toeic900.Fragment.PracticeFragment;
import com.peterwayne.toeic900.Fragment.RealTestFragment;
import com.peterwayne.toeic900.Fragment.ReviewFragment;
import com.peterwayne.toeic900.Fragment.StatisticFragment;

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
            case 4: return new ProfileFragment();
            default: return new PracticeFragment();
        }

    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
