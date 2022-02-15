package com.example.a900toeic.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.a900toeic.Database.DBQuery;
import com.example.a900toeic.Fragment.PartTwoFragment;

public class ViewPagerPartTwoAdapter extends FragmentStateAdapter {
    public ViewPagerPartTwoAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        PartTwoFragment fragment = new PartTwoFragment();
        fragment.setData(DBQuery.questionPartTwoList.get(position));
        return fragment;
    }

    @Override
    public int getItemCount() {
        return DBQuery.questionPartTwoList.size();
    }
}
