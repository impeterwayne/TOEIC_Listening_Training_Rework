package com.peterwayne.toeic900.Fragment;

import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.peterwayne.toeic900.Activity.TrainingActivity;
import com.peterwayne.toeic900.Database.DBQuery;
import com.peterwayne.toeic900.LocalData.RoomDbManager;
import com.peterwayne.toeic900.LocalData.QuestionPartThreeAndFourStatus;
import com.peterwayne.toeic900.Model.QuestionPartThreeAndFour;
import com.peterwayne.toeic900.R;

import java.util.HashMap;
import java.util.Objects;


public class PartThreeAndFourFragment extends Fragment {
    protected HashMap<AppCompatButton,String> keyButtonsMap1, keyButtonsMap2, keyButtonsMap3;
    protected TextView txt_question1, txt_question2, txt_question3;
    protected QuestionPartThreeAndFour data;
    protected int countSolvedQuestions;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        countSolvedQuestions =0;
        View view =  inflater.inflate(R.layout.fragment_part_three_and_four, container, false);
        addControls(view);
        addEvents();
        return view;
    }

    private void addEvents() {
        for(final AppCompatButton key1 : keyButtonsMap1.keySet())
        {
            key1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    processAnswer(key1,keyButtonsMap1,data.getKey1());
                }
            });
        }
        for(final AppCompatButton key2 : keyButtonsMap2.keySet())
        {
            key2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    processAnswer(key2,keyButtonsMap2,data.getKey2());
                }
            });
        }
        for(final AppCompatButton key3 : keyButtonsMap3.keySet())
        {
            key3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    processAnswer(key3,keyButtonsMap3,data.getKey3());
                }
            });
        }

    }

    protected void processAnswer(final AppCompatButton key,
                               final HashMap<AppCompatButton, String> keyMap,
                               final String correctKey) {
        updateUI(key, keyMap, correctKey);
        for(final AppCompatButton disableKey : keyMap.keySet())
        {
            disableKey.setClickable(false);
        }
        countSolvedQuestions++;
        if(countSolvedQuestions ==3)
        {
            addDoneQuestion();
            DBQuery.updatePracticeStatistic();
            DBQuery.updateTotalPractice();
            ((TrainingActivity)getActivity()).getBtnShowScript().setVisibility(VISIBLE);
        }
    }
    private void addDoneQuestion() {
        QuestionPartThreeAndFourStatus doneQuestion = new QuestionPartThreeAndFourStatus();
        doneQuestion.setId(data.getId());
        doneQuestion.setNumber(data.getNumber());
        doneQuestion.setImage_url(data.getImage_url());
        doneQuestion.setAudio_url(data.getAudio_url());

        doneQuestion.setQuestion1(data.getQuestion1());
        doneQuestion.setQuestion2(data.getQuestion2());
        doneQuestion.setQuestion3(data.getQuestion3());

        doneQuestion.setNumber1(data.getNumber1());
        doneQuestion.setNumber2(data.getNumber2());
        doneQuestion.setNumber3(data.getNumber3());

        doneQuestion.setKey1(data.getKey1());
        doneQuestion.setKey2(data.getKey2());
        doneQuestion.setKey3(data.getKey3());

        doneQuestion.setScript(data.getScript());

        doneQuestion.setScript_key1A(data.getScript_key1A());
        doneQuestion.setScript_key1B(data.getScript_key1B());
        doneQuestion.setScript_key1C(data.getScript_key1C());
        doneQuestion.setScript_key1D(data.getScript_key1D());

        doneQuestion.setScript_key2A(data.getScript_key2A());
        doneQuestion.setScript_key2B(data.getScript_key2B());
        doneQuestion.setScript_key2C(data.getScript_key2C());
        doneQuestion.setScript_key2D(data.getScript_key2D());

        doneQuestion.setScript_key3A(data.getScript_key3A());
        doneQuestion.setScript_key3B(data.getScript_key3B());
        doneQuestion.setScript_key3C(data.getScript_key3C());
        doneQuestion.setScript_key3D(data.getScript_key3D());


        doneQuestion.setDone(true);
        RoomDbManager.getInstance(getContext()).statusDAO().addDoneQuestion(doneQuestion);
    }
    protected void updateUI(AppCompatButton key, HashMap<AppCompatButton, String> keyMap, String correctKey) {
        if(Objects.equals(keyMap.get(key), correctKey))
        {
            key.setBackgroundResource(R.drawable.bg_right_answer);
        }else
        {
            key.setBackgroundResource(R.drawable.bg_wrong_answer);
            for(final AppCompatButton keyButton : keyMap.keySet())
            {
                if(Objects.equals(keyMap.get(keyButton), correctKey))
                {
                    keyButton.setBackgroundResource(R.drawable.bg_right_answer);
                }
            }
        }
    }
    private void addControls(View view) {
        txt_question1 = view.findViewById(R.id.txt_question1);
        txt_question2 = view.findViewById(R.id.txt_question2);
        txt_question3 = view.findViewById(R.id.txt_question3);
        initKeyMap();
        //key map question 1
        keyButtonsMap1.put(view.findViewById(R.id.btn_key1A),"A");
        keyButtonsMap1.put(view.findViewById(R.id.btn_key1B),"B");
        keyButtonsMap1.put(view.findViewById(R.id.btn_key1C),"C");
        keyButtonsMap1.put(view.findViewById(R.id.btn_key1D),"D");
        //key map question 2
        keyButtonsMap2.put(view.findViewById(R.id.btn_key2A),"A");
        keyButtonsMap2.put(view.findViewById(R.id.btn_key2B),"B");
        keyButtonsMap2.put(view.findViewById(R.id.btn_key2C),"C");
        keyButtonsMap2.put(view.findViewById(R.id.btn_key2D),"D");
        //key map question 3
        keyButtonsMap3.put(view.findViewById(R.id.btn_key3A),"A");
        keyButtonsMap3.put(view.findViewById(R.id.btn_key3B),"B");
        keyButtonsMap3.put(view.findViewById(R.id.btn_key3C),"C");
        keyButtonsMap3.put(view.findViewById(R.id.btn_key3D),"D");

        loadDataToView(view);
    }

    private void initKeyMap() {
        keyButtonsMap1 = new HashMap<>();
        keyButtonsMap2 = new HashMap<>();
        keyButtonsMap3 = new HashMap<>();
    }

    public void loadDataToView(final View view)
    {
        //data question 1
        txt_question1.setText(data.getNumber1()+ ". " +data.getQuestion1());
        ((AppCompatButton)view.findViewById(R.id.btn_key1A)).setText(data.getScript_key1A());
        ((AppCompatButton)view.findViewById(R.id.btn_key1B)).setText(data.getScript_key1B());
        ((AppCompatButton)view.findViewById(R.id.btn_key1C)).setText(data.getScript_key1C());
        ((AppCompatButton)view.findViewById(R.id.btn_key1D)).setText(data.getScript_key1D());
        // data question 2
        txt_question2.setText(data.getNumber2()+ ". " +data.getQuestion2());
        ((AppCompatButton)view.findViewById(R.id.btn_key2A)).setText(data.getScript_key2A());
        ((AppCompatButton)view.findViewById(R.id.btn_key2B)).setText(data.getScript_key2B());
        ((AppCompatButton)view.findViewById(R.id.btn_key2C)).setText(data.getScript_key2C());
        ((AppCompatButton)view.findViewById(R.id.btn_key2D)).setText(data.getScript_key2D());
        // data question 3
        txt_question3.setText(data.getNumber3()+ ". " +data.getQuestion3());
        ((AppCompatButton)view.findViewById(R.id.btn_key3A)).setText(data.getScript_key3A());
        ((AppCompatButton)view.findViewById(R.id.btn_key3B)).setText(data.getScript_key3B());
        ((AppCompatButton)view.findViewById(R.id.btn_key3C)).setText(data.getScript_key3C());
        ((AppCompatButton)view.findViewById(R.id.btn_key3D)).setText(data.getScript_key3D());
    }
    public void setData(QuestionPartThreeAndFour data)
    {
        this.data = data;
    }
}