package com.peterwayne.toeic900.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.peterwayne.toeic900.Fragment.PartThreeAndFourFragment;
import com.peterwayne.toeic900.Model.QuestionPartThreeAndFour;

import java.util.List;

public class TrainingPartThreeAndFourAdapter extends FragmentStateAdapter {
    List<QuestionPartThreeAndFour> data;
    public TrainingPartThreeAndFourAdapter(@NonNull FragmentActivity fragmentActivity, List<QuestionPartThreeAndFour> data) {
        super(fragmentActivity);
        this.data = data;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        PartThreeAndFourFragment fragment = new PartThreeAndFourFragment();
        fragment.setData( data.get(position));
        return fragment;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
