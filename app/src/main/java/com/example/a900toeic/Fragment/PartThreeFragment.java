package com.example.a900toeic.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.a900toeic.Model.QuestionPartThreeAndFour;
import com.example.a900toeic.R;


public class PartThreeFragment extends Fragment {
    private QuestionPartThreeAndFour data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_part_three, container, false);
        addControls(view);
        return view;
    }

    private void addControls(View view) {
    }
    public void setData(QuestionPartThreeAndFour data)
    {
        this.data = data;
    }
}