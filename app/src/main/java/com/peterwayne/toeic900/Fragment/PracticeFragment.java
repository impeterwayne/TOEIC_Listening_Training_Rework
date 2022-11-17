package com.peterwayne.toeic900.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.peterwayne.toeic900.Adapter.CategoryAdapter;
import com.peterwayne.toeic900.R;
import com.google.firebase.auth.FirebaseAuth;


public class PracticeFragment extends Fragment {
    private ImageView img_avatar;
    private TextView txt_fullName;
    private GridView lv_categories;
    private CategoryAdapter categoryAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_practice, container, false);
        addControls(view);
        addEvents();
        return view;
    }

    private void addEvents() {
        Glide.with(this).load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).centerCrop().into(img_avatar);
        txt_fullName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

    }

    private void addControls(View view) {
        img_avatar = view.findViewById(R.id.img_avatar);
        txt_fullName = view.findViewById(R.id.txt_fullName);
        lv_categories = view.findViewById(R.id.lv_categories);
        categoryAdapter = new CategoryAdapter(getContext(),R.layout.item_category,CategoryAdapter.categories);
        lv_categories.setAdapter(categoryAdapter);
    }
}