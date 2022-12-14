package com.peterwayne.toeic900.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
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
                        logOut();
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                        return true;
                    case R.id.action_setTarget:
                        showTargetDialog();
                    default:
                        return false;
                }
            }
        });
        popup.show();
    }

    private void showTargetDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_set_target);
        Window window = dialog.getWindow();
        if (window==null)
        {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttrs = window.getAttributes();
        windowAttrs.gravity = Gravity.CENTER;
        window.setAttributes(windowAttrs);
        NumberPicker targetPicker = dialog.findViewById(R.id.np_target);
        AppCompatButton btn_apply = dialog.findViewById(R.id.btn_apply);
        targetPicker.setMinValue(0);
        targetPicker.setMaxValue(495);
        targetPicker.setValue(Integer.parseInt(txt_target.getText().toString()));
        dialog.show();
        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int target = targetPicker.getValue();
                DBQuery.updateTarget(target);
                loadUserData();
                dialog.dismiss();
            }
        });

    }

    private void logOut() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();
    }

    private void loadUserData() {
        Glide.with(getActivity()).load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).into(img_avatar);
        txt_fullName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        DBQuery.db.collection("User").document(Utils.getFirebaseUser()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                loadPractice(documentSnapshot);
                loadTests(documentSnapshot);
                loadTarget(documentSnapshot);
            }
        });
    }

    private void loadTarget(DocumentSnapshot documentSnapshot) {
        if(documentSnapshot.get("target")==null)
        {
            txt_target.setText("0");
        }
        else
        {
            Integer target = documentSnapshot.get("target",Integer.class);
            txt_target.setText(target.toString());
        }
    }
    private void loadTests(DocumentSnapshot documentSnapshot) {
        if(documentSnapshot.get("tests")==null)
        {
            txt_numTests.setText("0");
        }else
        {
            Integer num_solved_tests = documentSnapshot.get("tests", Integer.class);
            txt_numTests.setText(num_solved_tests.toString());
        }
    }

    private void loadPractice(final DocumentSnapshot documentSnapshot) {
        if(documentSnapshot.get("practice")==null)
        {
            txt_numPractice.setText("0");
        }else
        {
            Integer num_solved_questions = documentSnapshot.get("practice", Integer.class);
            txt_numPractice.setText(num_solved_questions.toString());
        }
    }

    private void addControls(View view) {
        img_avatar = view.findViewById(R.id.img_avatar);
        txt_fullName = view.findViewById(R.id.txt_fullName);
        txt_numPractice = view.findViewById(R.id.txt_numPractice);
        txt_numTests = view.findViewById(R.id.txt_numTests);
        txt_target = view.findViewById(R.id.txt_target);
        btn_setting = view.findViewById(R.id.btn_setting);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadUserData();
    }
}