package com.peterwayne.toeic900.Adapter;

import static com.peterwayne.toeic900.Utils.Utils.ID_PART_FOUR_TRAINING;
import static com.peterwayne.toeic900.Utils.Utils.ID_PART_ONE_TRAINING;
import static com.peterwayne.toeic900.Utils.Utils.ID_PART_THREE_TRAINING;
import static com.peterwayne.toeic900.Utils.Utils.ID_PART_TWO_TRAINING;
import static com.peterwayne.toeic900.Utils.Utils.PART_ID;

import android.app.Activity;
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

import com.peterwayne.toeic900.Activity.TrainingActivity;
import com.peterwayne.toeic900.Model.Category;
import com.peterwayne.toeic900.R;

public class CategoryAdapter extends ArrayAdapter<Category> {
    private int resource;
    private Context context;
    public static Category[] categories = {
            new Category(1, "Part 1", "Photographs", R.drawable.ic_part1),
            new Category(2, "Part 2", "Question - Response", R.drawable.ic_part2),
            new Category(3, "Part 3", "Short conversation", R.drawable.ic_part3),
            new Category(4, "Part 4", "Talks", R.drawable.ic_part4)
    };
    public CategoryAdapter(@NonNull Context context, int resource, @NonNull Category[] objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
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
            TextView txt_category_name = view.findViewById(R.id.txt_cat_name);
            TextView txt_category_description = view.findViewById(R.id.txt_cat_description);
            ImageView img_category_icon = view.findViewById(R.id.img_cat_icon);
            txt_category_name.setText(category.getName());
            txt_category_description.setText(category.getDescription());
            img_category_icon.setImageResource(category.getIcon());
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TrainingActivity.class);
                if (category != null) {
                    switch (category.getId()) {
                        case 1:
                            intent.putExtra(PART_ID,ID_PART_ONE_TRAINING);
                            break;
                        case 2:
                            intent.putExtra(PART_ID,ID_PART_TWO_TRAINING);
                            break;
                        case 3:
                            intent.putExtra(PART_ID,ID_PART_THREE_TRAINING);
                            break;
                        case 4:
                            intent.putExtra(PART_ID,ID_PART_FOUR_TRAINING);
                            break;
                    }
                }
                getContext().startActivity(intent);
                ((Activity) getContext()).finish();
            }
        });
        return view;
    }

}
