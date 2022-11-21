package com.peterwayne.toeic900.Fragment;

import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.peterwayne.toeic900.LocalData.RoomDbManager;
import com.peterwayne.toeic900.LocalData.QuestionPartTwoStatus;
import com.peterwayne.toeic900.Model.QuestionPartTwo;
import com.peterwayne.toeic900.R;

import java.util.HashMap;
import java.util.Objects;


public class PartTwoFragment extends Fragment {
    protected HashMap<AppCompatButton, String> keyButtonsMap;
    protected TextView txt_script_part_two, txt_number;
    protected QuestionPartTwo data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_part_two, container, false);
        addControls(view);
        addEvents(view);
        return view;
    }

    protected void addEvents(View view) {
        for (AppCompatButton key : keyButtonsMap.keySet()) {
            key.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    processAnswer(key);
                    addDoneQuestion();
                }
            });
        }
    }
    private void addDoneQuestion() {
        QuestionPartTwoStatus doneQuestion = new QuestionPartTwoStatus();
        doneQuestion.setId(data.getId());
        doneQuestion.setNumber(data.getNumber());
        doneQuestion.setKey(data.getKey());
        doneQuestion.setScript(data.getScript());
        doneQuestion.setAudio_url(data.getAudio_url());
        doneQuestion.setScript_keyA(data.getScript_keyA());
        doneQuestion.setScript_keyB(data.getScript_keyB());
        doneQuestion.setScript_keyC(data.getScript_keyC());
        doneQuestion.setDone(true);
        RoomDbManager.getInstance(getContext()).statusDAO().addDoneQuestion(doneQuestion);
    }
    private void addControls(View view) {
        txt_script_part_two = view.findViewById(R.id.txt_script_part_two);
        txt_number = view.findViewById(R.id.txt_number);
        keyButtonsMap = new HashMap<>();
        keyButtonsMap.put(view.findViewById(R.id.btn_key1A), "A");
        keyButtonsMap.put(view.findViewById(R.id.btn_key1B), "B");
        keyButtonsMap.put(view.findViewById(R.id.btn_key1C), "C");
    }
    protected void processAnswer(final AppCompatButton btnKeyClick)
    {
        updateUI(btnKeyClick);
        for (AppCompatButton key : keyButtonsMap.keySet()) {
            key.setClickable(false);
        }
        View t = getActivity().findViewById(R.id.btn_show_script);
        t.setVisibility(VISIBLE);
        txt_script_part_two.setText(data.getScript());
    }

    protected void updateUI(final AppCompatButton btnKeyClick) {
        if (Objects.equals(keyButtonsMap.get(btnKeyClick), data.getKey())) {
            btnKeyClick.setBackgroundResource(R.drawable.bg_right_answer);
        } else {
            btnKeyClick.setBackgroundResource(R.drawable.bg_wrong_answer);
            for (AppCompatButton key : keyButtonsMap.keySet()) {
                if (Objects.equals(keyButtonsMap.get(key), data.getKey())) {
                    key.setBackgroundResource(R.drawable.bg_right_answer);
                }
            }
        }
    }
    public void setData(QuestionPartTwo data) {
        this.data = data;
    }
}