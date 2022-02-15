package com.example.a900toeic.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.a900toeic.Database.DBQuery;
import com.example.a900toeic.Fragment.PartThreeFragment;

public class ViewPagerPartThreeAdapter extends FragmentStateAdapter {
    public ViewPagerPartThreeAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        PartThreeFragment fragment = new PartThreeFragment();
        fragment.setData(DBQuery.questionPartThreeList.get(position));
        return fragment;
    }

    @Override
    public int getItemCount() {
        return DBQuery.questionPartThreeList.size();
    }
}
