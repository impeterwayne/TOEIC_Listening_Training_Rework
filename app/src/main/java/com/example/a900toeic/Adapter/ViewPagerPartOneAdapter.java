package com.example.a900toeic.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.a900toeic.Database.DBQuery;
import com.example.a900toeic.Fragment.PartOneFragment;

public class ViewPagerPartOneAdapter extends FragmentStateAdapter {
    public ViewPagerPartOneAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        PartOneFragment fragment = new PartOneFragment();
        fragment.setData(DBQuery.questionPartOneList.get(position));
        return fragment;
    }
    @Override
    public int getItemCount() {
        return DBQuery.questionPartOneList.size();
    }

}
