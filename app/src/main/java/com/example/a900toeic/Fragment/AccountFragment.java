package com.example.a900toeic.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.a900toeic.Activity.LoginActivity;
import com.example.a900toeic.R;
import com.google.firebase.auth.FirebaseAuth;


public class AccountFragment extends Fragment {
    private Button btn_log_out;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account, container, false);
        addControls(view);
        addEvents(view);
        return view;
    }

    private void addEvents(View view) {
        btn_log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              FirebaseAuth.getInstance().signOut();
              startActivity(new Intent(getContext(), LoginActivity.class));
              getActivity().finish();
            }
        });
    }

    private void addControls(View view) {
        btn_log_out = view.findViewById(R.id.btn_log_out);
    }
}