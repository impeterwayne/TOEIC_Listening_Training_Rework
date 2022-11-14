package com.peterwayne.toeic900.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.peterwayne.toeic900.Activity.MainActivity;
import com.peterwayne.toeic900.Activity.TrainingActivity;
import com.peterwayne.toeic900.R;

public class ReviewFragment extends Fragment {

    private AppCompatButton btn_review_part1,btn_review_part2, btn_review_part3,btn_review_part4;
    private Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        addControls(view);
        addEvents();
        return view;
    }


    private void addEvents() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();
            }
        });
        btn_review_part1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TrainingActivity.class);
                intent.putExtra("part",11);
                startActivity(intent);
                getActivity().finish();
            }
        });
        btn_review_part2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TrainingActivity.class);
                intent.putExtra("part",12);
                startActivity(intent);
                getActivity().finish();
            }
        });
        btn_review_part3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TrainingActivity.class);
                intent.putExtra("part",13);
                startActivity(intent);
                getActivity().finish();
            }
        });
        btn_review_part4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TrainingActivity.class);
                intent.putExtra("part",14);
                startActivity(intent);
                getActivity().finish();
            }
        });

    }

    private void addControls(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        btn_review_part1 = view.findViewById(R.id.btn_review_part1);
        btn_review_part2 = view.findViewById(R.id.btn_review_part2);
        btn_review_part3 = view.findViewById(R.id.btn_review_part3);
        btn_review_part4 = view.findViewById(R.id.btn_review_part4);
    }
}