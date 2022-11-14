package com.peterwayne.toeic900.Fragment;
import static android.view.View.VISIBLE;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.peterwayne.toeic900.Activity.TrainingActivity;
import com.peterwayne.toeic900.LocalData.DataLocalManager;
import com.peterwayne.toeic900.Model.QuestionPartOne;
import com.peterwayne.toeic900.R;
import java.util.HashMap;
import java.util.Objects;


public class PartOneFragment extends Fragment {
    protected ImageView img_part_one;
    protected TextView txt_number;
    protected HashMap<AppCompatButton, String> keyButtonsMap;
    protected QuestionPartOne data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_part_one, container, false);
        addControls(view);
        addEvents();
        return view;
    }

    private void addEvents() {
        for (AppCompatButton key : keyButtonsMap.keySet()) {
            key.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    processAnswer(key);
                    DataLocalManager.addDoneQuestion(data.getId());
                }
            });
        }

    }

    private void addControls(View view) {
        img_part_one = view.findViewById(R.id.img_part_one);
        txt_number = view.findViewById(R.id.txt_number);
        keyButtonsMap = new HashMap<>();
        keyButtonsMap.put(view.findViewById(R.id.btn_key1A), "A");
        keyButtonsMap.put(view.findViewById(R.id.btn_key1B), "B");
        keyButtonsMap.put(view.findViewById(R.id.btn_key1C), "C");
        keyButtonsMap.put(view.findViewById(R.id.btn_key1D), "D");
        passDataToView();

    }

    protected void passDataToView() {
        Glide.with(PartOneFragment.this).load(data.getImage_url()).centerCrop().into(img_part_one);
    }

    public void setData(QuestionPartOne data) {
        this.data = data;
    }

    public void processAnswer(AppCompatButton btnKeyClick) {
        updateUI(btnKeyClick);
        for (AppCompatButton key : keyButtonsMap.keySet()) {
            key.setClickable(false);
        }

        ((TrainingActivity)getActivity()).getBtnShowScript().setVisibility(VISIBLE);
    }

    protected void updateUI(AppCompatButton btnKeyClick) {
        if (Objects.equals(keyButtonsMap.get(btnKeyClick), data.getKey())) {
            btnKeyClick.setBackgroundResource(R.drawable.bg_right_answer);
        } else {
            btnKeyClick.setBackgroundResource(R.drawable.bg_wrong_answer);
            for (AppCompatButton key : keyButtonsMap.keySet()) {
                if (Objects.equals(keyButtonsMap.get(key), data.getKey())) {
                    key.setBackgroundResource(R.drawable.bg_right_answer);
                }
            }
        }
    }
}