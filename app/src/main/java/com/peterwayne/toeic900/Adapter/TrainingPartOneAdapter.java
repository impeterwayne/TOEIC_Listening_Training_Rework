package com.peterwayne.toeic900.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.peterwayne.toeic900.Fragment.PartOneFragment;
import com.peterwayne.toeic900.Model.QuestionPartOne;
import java.util.List;

public class TrainingPartOneAdapter extends FragmentStateAdapter {
    private final List<QuestionPartOne> data;
    public TrainingPartOneAdapter(@NonNull FragmentActivity fragmentActivity, List<QuestionPartOne> data) {
        super(fragmentActivity);
        this.data = data;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        PartOneFragment fragment = new PartOneFragment();
        fragment.setData(data.get(position));
        return fragment;
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

}
