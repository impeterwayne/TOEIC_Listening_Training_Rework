package com.peterwayne.toeic900.Fragment;

import static com.peterwayne.toeic900.Utils.Utils.ID_REVIEW_TRAINING;
import static com.peterwayne.toeic900.Utils.Utils.PART_ID;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import com.peterwayne.toeic900.Activity.MainActivity;
import com.peterwayne.toeic900.Activity.TrainingActivity;
import com.peterwayne.toeic900.LocalData.LocalData;
import com.peterwayne.toeic900.R;

public class ReviewFragment extends Fragment {

    private Toolbar toolbar;
    private TextView txt_num_revision;
    private AppCompatButton btn_review;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        addControls(view);
        updateNumberToReview();
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
        btn_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TrainingActivity.class);
                intent.putExtra(PART_ID,ID_REVIEW_TRAINING);
                startActivity(intent);
            }
        });

    }

    private void updateNumberToReview() {
        int numberQuestionToReview = LocalData.getInstance(getActivity()).statusDAO().getReviewPartOne().size()+
        LocalData.getInstance(getActivity()).statusDAO().getReviewPartTwo().size()+
        LocalData.getInstance(getActivity()).statusDAO().getReviewPartThreeAndFour().size();
        String notification = "";
        if(numberQuestionToReview <= 0)
        {
            notification ="There is no question to review";
            btn_review.setVisibility(View.GONE);
        }else
        {
            if(numberQuestionToReview == 1 )
            {
                notification ="There is one question to review";
            }else
            {
                notification = new StringBuilder().append("There are ")
                                                  .append(numberQuestionToReview)
                                                  .append(" questions to review").toString();
            }
            btn_review.setVisibility(View.VISIBLE);
        }
        txt_num_revision.setText(notification);
    }

    private void addControls(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        txt_num_revision = view.findViewById(R.id.txt_num_revision);
        btn_review = view.findViewById(R.id.btn_review);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateNumberToReview();
    }
}