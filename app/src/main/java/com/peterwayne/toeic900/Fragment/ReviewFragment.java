package com.peterwayne.toeic900.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import com.peterwayne.toeic900.Activity.MainActivity;
import com.peterwayne.toeic900.R;

public class ReviewFragment extends Fragment {

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

    }

    private void addControls(View view) {
        toolbar = view.findViewById(R.id.toolbar);
    }
}