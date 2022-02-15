package com.example.a900toeic.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.a900toeic.LocalData.DataLocalManager;
import com.example.a900toeic.Model.QuestionPartOne;
import com.example.a900toeic.Model.QuestionPartTwo;
import com.example.a900toeic.R;


public class PartTwoFragment extends Fragment {
    private AppCompatButton btn_keyA, btn_keyB, btn_keyC;
    private TextView txt_script_part_two, txt_script_keyA, txt_script_keyB,txt_script_keyC;
    private QuestionPartTwo data;
    private String keyAnswerClick;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_part_two, container, false);
        addControls(view);
        addEvents(view);
        return view;
    }

    private void addEvents(View view) {
        btn_keyA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyAnswerClick ="A";
                processAnswer(keyAnswerClick,btn_keyA);
                DataLocalManager.addDoneQuestion(data.getId());
            }
        });
        btn_keyB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyAnswerClick ="B";
                processAnswer(keyAnswerClick,btn_keyB);
                DataLocalManager.addDoneQuestion(data.getId());
            }
        });
        btn_keyC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyAnswerClick ="C";
                processAnswer(keyAnswerClick,btn_keyC);
                DataLocalManager.addDoneQuestion(data.getId());
            }
        });
    }

    private void addControls(View view) {
        btn_keyA = view.findViewById(R.id.btn_keyA);
        btn_keyB = view.findViewById(R.id.btn_keyB);
        btn_keyC = view.findViewById(R.id.btn_keyC);
        txt_script_part_two = view.findViewById(R.id.txt_script_part_two);
        txt_script_keyA = view.findViewById(R.id.txt_script_keyA);
        txt_script_keyB = view.findViewById(R.id.txt_script_keyB);
        txt_script_keyC = view.findViewById(R.id.txt_script_keyC);
        putDataToView();
    }
    public void processAnswer(String keyAnswerClick, AppCompatButton btn_key)
    {
        if(keyAnswerClick.equals(data.getKey()))
        {
            btn_key.setBackgroundResource(R.drawable.bg_right_answer);
        }else
        {
            btn_key.setBackgroundResource(R.drawable.bg_wrong_answer);
        }
        txt_script_part_two.setText(data.getScript());
        txt_script_keyA.setVisibility(View.VISIBLE);
        txt_script_keyB.setVisibility(View.VISIBLE);
        txt_script_keyC.setVisibility(View.VISIBLE);
        btn_keyA.setClickable(false);
        btn_keyB.setClickable(false);
        btn_keyC.setClickable(false);
    }
    public void putDataToView()
    {
        txt_script_keyA.setText(data.getKeyA());
        txt_script_keyB.setText(data.getKeyB());
        txt_script_keyC.setText(data.getKeyC());
    }
    public void setData(QuestionPartTwo data) {
        this.data = data;
    }
}