package com.peterwayne.toeic900.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;

import com.peterwayne.toeic900.Fragment.PartOneFragment;
import com.peterwayne.toeic900.Fragment.TestPartOneFragment;
import com.peterwayne.toeic900.Fragment.TestPartThreeAndFourFragment;
import com.peterwayne.toeic900.Fragment.TestPartTwoFragment;
import com.peterwayne.toeic900.Model.Question;
import com.peterwayne.toeic900.Model.QuestionPartOne;
import com.peterwayne.toeic900.Model.QuestionPartThreeAndFour;
import com.peterwayne.toeic900.Model.QuestionPartTwo;

import java.util.List;

public class RealTestAdapter extends FragmentStateAdapter {
    private List<Question> data;

    public RealTestAdapter(@NonNull FragmentActivity fragmentActivity, List<Question> data) {
        super(fragmentActivity);
        this.data = data;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(data.get(position) instanceof QuestionPartOne)
        {
            TestPartOneFragment fragment = new TestPartOneFragment();
            fragment.setData( (QuestionPartOne) data.get(position));
            return fragment;
        }else if (data.get(position) instanceof QuestionPartTwo)
        {
            TestPartTwoFragment fragment = new TestPartTwoFragment();
            fragment.setData( (QuestionPartTwo) data.get(position));
            return fragment;
        }
        else if (data.get(position) instanceof QuestionPartThreeAndFour)
        {
            TestPartThreeAndFourFragment fragment = new TestPartThreeAndFourFragment();
            fragment.setData( (QuestionPartThreeAndFour) data.get(position));
            return fragment;
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
