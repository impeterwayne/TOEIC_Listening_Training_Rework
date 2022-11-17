package com.peterwayne.toeic900.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.peterwayne.toeic900.Fragment.EmptyFragment;
import com.peterwayne.toeic900.Fragment.PartOneFragment;
import com.peterwayne.toeic900.Fragment.PartThreeAndFourFragment;
import com.peterwayne.toeic900.Fragment.PartTwoFragment;
import com.peterwayne.toeic900.Fragment.TestPartOneFragment;
import com.peterwayne.toeic900.Fragment.TestPartThreeAndFourFragment;
import com.peterwayne.toeic900.Fragment.TestPartTwoFragment;
import com.peterwayne.toeic900.LocalData.QuestionPartOneStatus;
import com.peterwayne.toeic900.LocalData.QuestionPartThreeAndFourStatus;
import com.peterwayne.toeic900.LocalData.QuestionPartTwoStatus;
import com.peterwayne.toeic900.Model.Question;
import com.peterwayne.toeic900.Model.QuestionPartOne;
import com.peterwayne.toeic900.Model.QuestionPartThreeAndFour;
import com.peterwayne.toeic900.Model.QuestionPartTwo;

import java.util.List;

public class RevisionAdapter extends FragmentStateAdapter {
    private List<Question> data;
    public RevisionAdapter(@NonNull FragmentActivity fragmentActivity, List<Question> data) {
        super(fragmentActivity);
        this.data = data;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(data.get(position) instanceof QuestionPartOneStatus)
        {
            PartOneFragment fragment = new PartOneFragment();
            fragment.setData( (QuestionPartOneStatus) data.get(position));
            return fragment;
        }else if (data.get(position) instanceof QuestionPartTwoStatus)
        {
            PartTwoFragment fragment = new PartTwoFragment();
            fragment.setData( (QuestionPartTwoStatus) data.get(position));
            return fragment;
        }
        else if (data.get(position) instanceof QuestionPartThreeAndFourStatus)
        {
            PartThreeAndFourFragment fragment = new PartThreeAndFourFragment();
            fragment.setData( (QuestionPartThreeAndFourStatus) data.get(position));
            return fragment;
        }
        return new EmptyFragment();

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
