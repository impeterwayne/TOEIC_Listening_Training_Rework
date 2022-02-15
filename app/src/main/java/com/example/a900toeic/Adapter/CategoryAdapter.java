package com.example.a900toeic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.a900toeic.Activity.TrainingActivity;
import com.example.a900toeic.Model.Category;
import com.example.a900toeic.R;

public class CategoryAdapter extends ArrayAdapter<Category> {
    private int resource;
    private Context context;
    public static Category[] objects = {
            new Category(1, "Part 1", "Choose the right answer", R.drawable.ic_part1),
            new Category(2, "Part 2", "Choose the right answer", R.drawable.ic_part1),
            new Category(3, "Part 3", "Choose the right answer", R.drawable.ic_part1),
            new Category(4, "Part 4", "Choose the right answer", R.drawable.ic_part1)
    };

    public CategoryAdapter(@NonNull Context context, int resource, @NonNull Category[] objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(resource, null);
        }
        Category category = getItem(position);
        if (category != null) {
            TextView txt_cat_name = view.findViewById(R.id.txt_cat_name);
            TextView txt_cat_description = view.findViewById(R.id.txt_cat_description);
            ImageView img_cat_icon = view.findViewById(R.id.img_cat_icon);
            txt_cat_name.setText(category.getName());
            txt_cat_description.setText(category.getDescription());
            img_cat_icon.setImageResource(category.getIcon());
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TrainingActivity.class);
                switch (category.getId()) {
                    case 1:
                        intent.putExtra("part",1);
                        break;
                    case 2:
                        intent.putExtra("part",2);
                        break;
                    case 3:
                        intent.putExtra("part",3);
                        break;
                    case 4:
                        intent.putExtra("part",4);
                        break;
                }
                getContext().startActivity(intent);
            }
        });
        return view;
    }

}
