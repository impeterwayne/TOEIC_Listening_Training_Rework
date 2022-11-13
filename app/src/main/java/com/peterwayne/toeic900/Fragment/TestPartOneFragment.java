package com.peterwayne.toeic900.Fragment;
import androidx.appcompat.widget.AppCompatButton;

import com.peterwayne.toeic900.Activity.RealTestActivity;
import com.peterwayne.toeic900.Model.Answer;
import java.util.List;
public class TestPartOneFragment extends PartOneFragment {
    @Override
    protected void passDataToView() {
        super.passDataToView();
        txt_number.setText(data.getNumber() + ".");
    }
    @Override
    public void processAnswer(AppCompatButton btnKeyClick){
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
            ((RealTestActivity) getActivity()).getAnswerMapAdapter().notifyItemChanged(position);
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
