package com.peterwayne.toeic900.Fragment;

import android.view.View;
import androidx.appcompat.widget.AppCompatButton;
import com.peterwayne.toeic900.Activity.RealTestActivity;
import com.peterwayne.toeic900.Model.Answer;

import java.util.List;

public class TestPartTwoFragment extends PartTwoFragment{
    @Override
    protected void addEvents(View view) {
        super.addEvents(view);
        txt_number.setText(data.getNumber()+".");
    }

    @Override
    protected void processAnswer(AppCompatButton btnKeyClick) {
        updateUI(btnKeyClick);
        final long answerNumber = data.getNumber();
        List<Answer> answerList = ((RealTestActivity) getActivity()).getAnswerList();
        for(Answer answer : answerList)
        {
            if(answer.getNumber() == answerNumber)
            {
                answer.setKey(data.getKey());
                answer.setKeyChoosed(keyButtonsMap.get(btnKeyClick));
            }
            int position =answerList.indexOf(answer);
            ((RealTestActivity) getActivity()).getAnswerSheetAdapter().notifyItemChanged(position);
        }
    }
    @Override
    protected void updateUI(AppCompatButton btnKeyClick) {
        for(AppCompatButton key : keyButtonsMap.keySet())
        {
            if(key != btnKeyClick)
            {
                key.setAlpha(0.3f);
            }
            btnKeyClick.setAlpha(1.0f);
        }
    }
}
