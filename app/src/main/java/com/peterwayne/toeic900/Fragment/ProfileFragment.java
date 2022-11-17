package com.peterwayne.toeic900.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.peterwayne.toeic900.Database.DBQuery;
import com.peterwayne.toeic900.R;
import com.peterwayne.toeic900.Utils.Utils;


public class ProfileFragment extends Fragment {
    private ImageView img_avatar;
    private TextView txt_fullName;
    private TextView txt_numPractice, txt_numTests, txt_target;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        addControls(view);
        addEvents();
        return view;
    }

    private void addEvents() {
        loadUserData();
    }

    private void loadUserData() {
        Glide.with(getActivity()).load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).into(img_avatar);
        txt_fullName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        DBQuery.db.collection("User").document(Utils.getFirebaseUser()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                txt_numPractice.setText(documentSnapshot.get("practice") + "");
                txt_numTests.setText(documentSnapshot.get("test") + "");
                txt_target.setText(documentSnapshot.get("target") + "");
            }
        });
    }


    private void addControls(View view) {
        img_avatar = view.findViewById(R.id.img_avatar);
        txt_fullName = view.findViewById(R.id.txt_fullName);
        txt_numPractice = view.findViewById(R.id.txt_numPractice);
        txt_numTests = view.findViewById(R.id.txt_numTests);
        txt_target = view.findViewById(R.id.txt_target);

    }
}