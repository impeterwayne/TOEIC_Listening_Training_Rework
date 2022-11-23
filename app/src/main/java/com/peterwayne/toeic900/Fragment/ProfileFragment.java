package com.peterwayne.toeic900.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.peterwayne.toeic900.Activity.LoginActivity;
import com.peterwayne.toeic900.Database.DBQuery;
import com.peterwayne.toeic900.R;
import com.peterwayne.toeic900.Utils.Utils;


public class ProfileFragment extends Fragment {
    private ImageView img_avatar, btn_setting;
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
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view);
            }
        });
    }

    private void showPopup(View v) {
        PopupMenu popup = new PopupMenu(getActivity(), v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.setting_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_logout:
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        auth.signOut();
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();
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
        btn_setting = view.findViewById(R.id.btn_setting);
    }
}