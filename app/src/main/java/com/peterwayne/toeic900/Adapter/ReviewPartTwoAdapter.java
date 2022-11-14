package com.peterwayne.toeic900.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.peterwayne.toeic900.Fragment.PartTwoFragment;
import com.peterwayne.toeic900.Model.QuestionPartTwo;

import java.util.List;

public class ReviewPartTwoAdapter extends FragmentStateAdapter {
    private List<QuestionPartTwo> data;
    public ReviewPartTwoAdapter(@NonNull FragmentActivity fragmentActivity, List<QuestionPartTwo> data) {
        super(fragmentActivity);
        this.data = data;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        PartTwoFragment fragment = new PartTwoFragment();
        fragment.setData(data.get(position));
        return fragment;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
