package com.peterwayne.toeic900.Fragment;

import static com.peterwayne.toeic900.Utils.Utils.ANSWER_STATE_CHOSEN;
import static com.peterwayne.toeic900.Utils.Utils.ANSWER_STATE_FADE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.peterwayne.toeic900.Activity.RealTestActivity;
import com.peterwayne.toeic900.Model.Answer;
import java.util.HashMap;
import java.util.List;

public class TestPartThreeAndFourFragment extends PartThreeAndFourFragment
{
    private String chosenKey1, chosenKey2, chosenKey3;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if(savedInstanceState!=null)
        {
            chosenKey1 = savedInstanceState.getString("chosenKey1");
            chosenKey2 = savedInstanceState.getString("chosenKey2");
            chosenKey3 = savedInstanceState.getString("chosenKey3");
        }
        super.onCreate(savedInstanceState);
    }

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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        for(AppCompatButton key : keyButtonsMap1.keySet())
        {
            if(key.getAlpha()== ANSWER_STATE_CHOSEN)
            {
                outState.putString("chosenKey1", keyButtonsMap1.get(key));
            }
        }
        for(AppCompatButton key : keyButtonsMap2.keySet())
        {
            if(key.getAlpha()== ANSWER_STATE_CHOSEN)
            {
                outState.putString("chosenKey2", keyButtonsMap2.get(key));
            }
        }
        for(AppCompatButton key : keyButtonsMap3.keySet())
        {
            if(key.getAlpha()== ANSWER_STATE_CHOSEN)
            {
                outState.putString("chosenKey3", keyButtonsMap3.get(key));
            }
        }
    }

    @Override
    protected void updateUI(final AppCompatButton key,
                            final HashMap<AppCompatButton, String> keyMap,
                            final String correctKey) {
        key.setAlpha(ANSWER_STATE_CHOSEN);
        for(AppCompatButton keyButton : keyMap.keySet())
        {
            if(keyButton!=key)
            {
                keyButton.setAlpha(ANSWER_STATE_FADE);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        for(AppCompatButton key : keyButtonsMap1.keySet())
        {
            if(chosenKey1!=null)
            {
                if(!chosenKey1.equals(keyButtonsMap1.get(key))) {
                    key.setAlpha(ANSWER_STATE_FADE);
                }else
                {
                    key.setAlpha(ANSWER_STATE_CHOSEN);
                }
            }
        }
        for(AppCompatButton key : keyButtonsMap2.keySet())
        {
            if(chosenKey2!=null)
            {
                if(!chosenKey2.equals(keyButtonsMap2.get(key))) {
                    key.setAlpha(ANSWER_STATE_FADE);
                }else
                {
                    key.setAlpha(ANSWER_STATE_CHOSEN);
                }
            }
        }
        for(AppCompatButton key : keyButtonsMap3.keySet())
        {
            if(chosenKey3!=null)
            {
                if(!chosenKey3.equals(keyButtonsMap3.get(key))) {
                    key.setAlpha(ANSWER_STATE_FADE);
                }else
                {
                    key.setAlpha(ANSWER_STATE_CHOSEN);
                }
            }
        }
    }
}
