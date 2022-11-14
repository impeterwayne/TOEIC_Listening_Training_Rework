package com.peterwayne.toeic900.Fragment;

import androidx.appcompat.widget.AppCompatButton;

import com.peterwayne.toeic900.Activity.RealTestActivity;
import com.peterwayne.toeic900.Model.Answer;
import java.util.HashMap;
import java.util.List;

public class TestPartThreeAndFourFragment extends PartThreeAndFourFragment
{
    @Override
    protected void processAnswer(AppCompatButton key, HashMap<AppCompatButton, String> keyMap, String correctKey) {
        updateUI(key, keyMap, correctKey);
        long answerNumber = data.getNumber();
        if (keyButtonsMap1.equals(keyMap)) {
            answerNumber = data.getNumber1();
        }else if(keyButtonsMap2.equals(keyMap))
        {
            answerNumber = data.getNumber2();
        }
        else if(keyButtonsMap3.equals(keyMap))
        {
            answerNumber = data.getNumber3();
        }
        List<Answer> answerList = ((RealTestActivity) getActivity()).getAnswerList();
        for(Answer answer : answerList)
        {
            if(answer.getNumber() == answerNumber)
            {
                answer.setKey(correctKey);
                answer.setKeyChoosed(keyMap.get(key));
            }
            int position = answerList.indexOf(answer);
            ((RealTestActivity) getActivity()).getAnswerSheetAdapter().notifyItemChanged(position);
        }
    }

    @Override
    protected void updateUI(final AppCompatButton key,
                            final HashMap<AppCompatButton, String> keyMap,
                            final String correctKey) {
        key.setAlpha(1.0f);
        for(AppCompatButton keyButton : keyMap.keySet())
        {
            if(keyButton!=key)
            {
                keyButton.setAlpha(0.3f);
            }
        }
    }
}
