package com.peterwayne.toeic900.Fragment;
import static com.peterwayne.toeic900.Utils.Utils.*;
import static com.peterwayne.toeic900.Utils.Utils.ANSWER_STATE_CHOOSED;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.peterwayne.toeic900.Activity.RealTestActivity;
import com.peterwayne.toeic900.Model.Answer;
import com.peterwayne.toeic900.Utils.Utils;

import java.util.List;
public class TestPartOneFragment extends PartOneFragment {
    private String chosenKey;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if(savedInstanceState!=null)
        {
            chosenKey = savedInstanceState.getString("chosenKey");
        }
        super.onCreate(savedInstanceState);
    }

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
            ((RealTestActivity) getActivity()).getAnswerSheetAdapter().notifyItemChanged(position);
        }
    }
    @Override
    protected void updateUI(AppCompatButton btnKeyClick) {
        for(AppCompatButton key : keyButtonsMap.keySet())
        {
            if(key != btnKeyClick)
            {
                key.setAlpha(ANSWER_STATE_FADE);
            }
        }
        btnKeyClick.setAlpha(ANSWER_STATE_CHOOSED);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        for(AppCompatButton key : keyButtonsMap.keySet())
        {
            if(key.getAlpha()==ANSWER_STATE_CHOOSED)
            {
                outState.putString("chosenKey", keyButtonsMap.get(key));
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        for(AppCompatButton key : keyButtonsMap.keySet())
        {
            if(chosenKey!=null)
            {
                if(!chosenKey.equals(keyButtonsMap.get(key))) {
                    key.setAlpha(ANSWER_STATE_FADE);
                }else
                {
                    key.setAlpha(ANSWER_STATE_CHOOSED);
                }
            }
        }
    }
}
