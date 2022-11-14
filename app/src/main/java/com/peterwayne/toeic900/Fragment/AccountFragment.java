package com.peterwayne.toeic900.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.peterwayne.toeic900.Activity.LoginActivity;
import com.peterwayne.toeic900.Database.DBQuery;
import com.peterwayne.toeic900.R;
import com.peterwayne.toeic900.Utils.Utils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;


public class AccountFragment extends Fragment {
    private Button btn_log_out;
    private ImageView img_avatar;
    private TextView txt_fullName;
    private TextView txt_highestScore, txt_goal;
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
        img_avatar = view.findViewById(R.id.img_avatar);
        txt_fullName = view.findViewById(R.id.txt_fullName);
        txt_highestScore =view.findViewById(R.id.txt_highestScore);
        txt_goal = view.findViewById(R.id.txt_goal);
        txt_fullName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        Glide.with(getContext()).load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).into(img_avatar);
        DocumentReference ref = DBQuery.db.collection("User").document(Utils.getFirebaseUser());
        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                txt_goal.setText(String.valueOf(documentSnapshot.get("goal")));
                txt_highestScore.setText(String.valueOf(documentSnapshot.get("max_score")));
            }
        });
    }
}