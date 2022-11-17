package com.peterwayne.toeic900.Activity;

import static com.peterwayne.toeic900.Utils.Utils.ID_PART_FOUR_TRAINING;
import static com.peterwayne.toeic900.Utils.Utils.ID_PART_ONE_TRAINING;
import static com.peterwayne.toeic900.Utils.Utils.ID_PART_THREE_TRAINING;
import static com.peterwayne.toeic900.Utils.Utils.ID_PART_TWO_TRAINING;
import static com.peterwayne.toeic900.Utils.Utils.ID_REVIEW_TRAINING;
import static com.peterwayne.toeic900.Utils.Utils.PART_ID;
import static com.peterwayne.toeic900.Utils.Utils.getTimeString;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.Slider;
import com.peterwayne.toeic900.Adapter.CategoryAdapter;
import com.peterwayne.toeic900.Adapter.RevisionAdapter;
import com.peterwayne.toeic900.Adapter.TrainingPartOneAdapter;
import com.peterwayne.toeic900.Adapter.TrainingPartThreeAndFourAdapter;
import com.peterwayne.toeic900.Adapter.TrainingPartTwoAdapter;
import com.peterwayne.toeic900.Database.DBQuery;
import com.peterwayne.toeic900.LocalData.LocalData;
import com.peterwayne.toeic900.LocalData.QuestionPartOneStatus;
import com.peterwayne.toeic900.LocalData.QuestionPartThreeAndFourStatus;
import com.peterwayne.toeic900.LocalData.QuestionPartTwoStatus;
import com.peterwayne.toeic900.LocalData.StatusDAO;
import com.peterwayne.toeic900.Model.Question;
import com.peterwayne.toeic900.Model.QuestionPartOne;
import com.peterwayne.toeic900.Model.QuestionPartThreeAndFour;
import com.peterwayne.toeic900.Model.QuestionPartTwo;
import com.peterwayne.toeic900.R;
import com.peterwayne.toeic900.Utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TrainingActivity extends AppCompatActivity {
    private Slider slider;
    private ImageView btn_backward, btn_forward,btn_play, btn_bookmark;
    private ImageView btn_next_question, btn_prev_question;
    private Toolbar toolbar;
    private TextView txt_toolbar_title,txt_toolbar_description;
    private TextView txt_timestamp;
    private ViewPager2 questionPager;
    private MediaPlayer mediaPlayer;
    private ProgressBar loadingBar;
    private AppCompatButton btn_show_script;
    private int partId;
    private Runnable runnable;
    private Handler handler;
    private final int[] positionArr = { R.id.btn_question_one, R.id.btn_question_two, R.id.btn_question_three,
                                        R.id.btn_question_four,R.id.btn_question_five};
    private ImageView[] positionButtons;
    private static StatusDAO localDataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        addControls();
        loadData();
    }
    private void initPositionButtons() {
        positionButtons = new ImageView[positionArr.length];
        for(int i =0 ;i <positionArr.length ;i++) {
            positionButtons[i] = findViewById(positionArr[i]);
        }
    }
    private <T extends Question> void addEvents(List<T> data) {
        loadingBar.setVisibility(View.GONE);
        for(int i = 0 ;i< positionButtons.length; i++)
        {
            final int currPos = i;
            positionButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    questionPager.setCurrentItem(currPos);
                }
            });
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer!=null)
                {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
                handler.removeCallbacks(runnable);
                finish();
            }
        });
            btn_forward.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+3000);
                }
            });
            btn_backward.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()-3000);
                }
            });
            btn_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mediaPlayer.isPlaying()) {
                        btn_play.setImageResource(R.drawable.ic_play);
                        mediaPlayer.pause();
                    } else {
                        btn_play.setImageResource(R.drawable.ic_pause);
                        mediaPlayer.start();
                    }
                }
            });
            btn_bookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = questionPager.getCurrentItem();
                    String questionId = data.get(position).getId();
                    if(localDataRef.getReviewPartOneById(questionId)==null &&
                            localDataRef.getReviewPartTwoById(questionId)==null &&
                            localDataRef.getReviewPartThreeAndFourById(questionId)==null) {
                        if(localDataRef.getDonePartOneById(questionId)!=null ||
                           localDataRef.getDonePartTwoById(questionId)!=null ||
                           localDataRef.getDonePartThreeAndFourById(questionId)!=null) {
                            addQuestionToReview(data.get(position));
                            updateBookmarkUI(data.get(position));
                        }else
                        {
                            Toast.makeText(TrainingActivity.this,
                                    "Please answer the question before bookmarking",Toast.LENGTH_SHORT).show();
                        }
                    }else
                    {
                        removeQuestionFromReview(data.get(position));
                        updateBookmarkUI(data.get(position));
                    }
                }
            });
            slider.setLabelFormatter(new LabelFormatter() {
                @NonNull
                @Override
                public String getFormattedValue(float value) {
                    return getTimeString((long)value);
                }
            });
            slider.addOnChangeListener(new Slider.OnChangeListener() {
                @Override
                public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                    if(fromUser)
                    {
                        mediaPlayer.seekTo((int) value);
                    }
                }
            });
            questionPager.setOffscreenPageLimit(4);
            questionPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    updatePagerNavigationUI(position);
                    updateQuestionNumberUI(position);
                    updateBookmarkUI(data.get(position));
                    updateShowScriptButtonVisibility(position);
                    playAudioFile(data,position);
                }

                private void updateShowScriptButtonVisibility(final int position) {
                    btn_show_script.setVisibility(View.GONE);
                }

                private void updateQuestionNumberUI(final int position) {
                    for(int i=0 ;i<positionArr.length; i++)
                    {
                        if(i!=position)
                        {
                            positionButtons[i].setAlpha(0.35f);
                        }else
                        {
                            positionButtons[i].setAlpha(1.0f);
                        }
                    }
                }

                private void updatePagerNavigationUI(final int position) {
                    if(position==0) btn_prev_question.setVisibility(View.GONE);
                    if(position==data.size()-1) btn_next_question.setVisibility(View.GONE);
                    if(position>0) btn_prev_question.setVisibility(View.VISIBLE);
                    if(position<data.size()-1) btn_next_question.setVisibility(View.VISIBLE);
                }
            });
            btn_prev_question.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int currPos = questionPager.getCurrentItem();
                    questionPager.setCurrentItem(currPos-1);
                }
            });
            btn_next_question.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int currPos = questionPager.getCurrentItem();
                    questionPager.setCurrentItem(currPos+1);
                }
            });
            btn_show_script.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showScriptDialog(data.get(questionPager.getCurrentItem()));
                }
            });

    }

    private void removeQuestionFromReview(final Question question) {
        if(question instanceof QuestionPartOne)
        {
            QuestionPartOneStatus reviewQuestion = localDataRef.getDonePartOneById(question.getId());
            reviewQuestion.setToReview(false);
            localDataRef.addToReview(reviewQuestion);
        }else if(question instanceof QuestionPartTwo)
        {
            QuestionPartTwoStatus reviewQuestion = localDataRef.getDonePartTwoById(question.getId());
            reviewQuestion.setToReview(false);
            localDataRef.addToReview(reviewQuestion);
        }else if(question instanceof QuestionPartThreeAndFour)
        {
            QuestionPartThreeAndFourStatus reviewQuestion = localDataRef.getDonePartThreeAndFourById(question.getId());
            reviewQuestion.setToReview(false);
            localDataRef.addToReview(reviewQuestion);
        }
    }

    private void addQuestionToReview(final Question question) {
        if(question instanceof QuestionPartOne)
        {
            QuestionPartOneStatus reviewQuestion = localDataRef.getDonePartOneById(question.getId());
            reviewQuestion.setToReview(true);
            localDataRef.addToReview(reviewQuestion);
        }else if(question instanceof QuestionPartTwo)
        {
            QuestionPartTwoStatus reviewQuestion = localDataRef.getDonePartTwoById(question.getId());
            reviewQuestion.setToReview(true);
            localDataRef.addToReview(reviewQuestion);
        }else if(question instanceof QuestionPartThreeAndFour)
        {
            QuestionPartThreeAndFourStatus reviewQuestion = localDataRef.getDonePartThreeAndFourById(question.getId());
            reviewQuestion.setToReview(true);
            localDataRef.addToReview(reviewQuestion);
        }
    }

    private <T extends Question>void updateBookmarkUI(final T question) {
        if(localDataRef.getReviewPartOneById(question.getId())==null &&
                localDataRef.getReviewPartTwoById(question.getId())==null &&
                localDataRef.getReviewPartThreeAndFourById(question.getId())==null)
        {
            btn_bookmark.setImageResource(R.drawable.ic_bookmark);
        }else
        {
            btn_bookmark.setImageResource(R.drawable.ic_bookmarked);
        }
    }

    private <T extends Question> void showScriptDialog(T data) {
        final Dialog dialog = new Dialog(TrainingActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (data instanceof QuestionPartOne) {
            dialog.setContentView(R.layout.dialog_script_part_one);
            setUpWindow(dialog);
            showScriptPartOne(dialog,data);
            dialog.show();
        } else if (data instanceof QuestionPartTwo) {
            dialog.setContentView(R.layout.dialog_script_part_two);
            setUpWindow(dialog);
            showScriptPartTwo(dialog,data);
            dialog.show();
        } else if (data instanceof QuestionPartThreeAndFour)
        {
            dialog.setContentView(R.layout.dialog_script_part_three_and_four);
            setUpWindow(dialog);
            showScriptPartThreeAndFour(dialog,data);
            dialog.show();
        }
    }

    private <T extends Question> void showScriptPartThreeAndFour(final Dialog dialog,final T data) {
        TextView txt_script = dialog.findViewById(R.id.txt_script);
        String script = ((QuestionPartThreeAndFour) data).getScript().replace("\\n","\n");
        txt_script.setText(script);
    }

    private <T extends Question> void showScriptPartTwo(final Dialog dialog,final T data) {
        TextView txt_script = dialog.findViewById(R.id.txt_script);
        TextView txt_scriptKeyA = dialog.findViewById(R.id.txt_scriptKeyA);
        TextView txt_scriptKeyB = dialog.findViewById(R.id.txt_scriptKeyB);
        TextView txt_scriptKeyC = dialog.findViewById(R.id.txt_scriptKeyC);
        txt_script.setText(((QuestionPartTwo) data).getScript());
        txt_scriptKeyA.setText(((QuestionPartTwo) data).getScript_keyA());
        txt_scriptKeyB.setText(((QuestionPartTwo) data).getScript_keyB());
        txt_scriptKeyC.setText(((QuestionPartTwo) data).getScript_keyC());
    }

    private <T extends Question> void showScriptPartOne(final Dialog dialog,final T data) {
        TextView txt_scriptKeyA = dialog.findViewById(R.id.txt_scriptKeyA);
        TextView txt_scriptKeyB = dialog.findViewById(R.id.txt_scriptKeyB);
        TextView txt_scriptKeyC = dialog.findViewById(R.id.txt_scriptKeyC);
        TextView txt_scriptKeyD = dialog.findViewById(R.id.txt_scriptKeyD);
        txt_scriptKeyA.setText(((QuestionPartOne) data).getScript_keyA());
        txt_scriptKeyB.setText(((QuestionPartOne) data).getScript_keyB());
        txt_scriptKeyC.setText(((QuestionPartOne) data).getScript_keyC());
        txt_scriptKeyD.setText(((QuestionPartOne) data).getScript_keyD());
    }

    private void setUpWindow(Dialog dialog) {
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
    }


    private void loadData() {
        partId = getIntent().getIntExtra(PART_ID,1);
        switch(partId)
        {
            case ID_PART_ONE_TRAINING:
                DBQuery.loadTestNameList(testList -> DBQuery.loadDataPartOne(TrainingActivity.this, testList, data -> {
                    loadUI(ID_PART_ONE_TRAINING);
                    TrainingPartOneAdapter mTrainingPartOneAdapter = new TrainingPartOneAdapter(TrainingActivity.this, data);
                    questionPager.setAdapter(mTrainingPartOneAdapter);
                    if(data.size()>0)
                    {
                        playAudioFile(data, 0);
                        updateBookmarkUI(data.get(0));
                        btn_prev_question.setVisibility(View.GONE);
                    }
                    addEvents(data);
                }));
                break;
            case ID_PART_TWO_TRAINING:
                DBQuery.loadTestNameList(testList -> DBQuery.loadDataPartTwo(TrainingActivity.this,testList, data -> {
                    loadUI(ID_PART_TWO_TRAINING);
                    TrainingPartTwoAdapter mTrainingPartTwoAdapter = new TrainingPartTwoAdapter(TrainingActivity.this, data);
                    questionPager.setAdapter(mTrainingPartTwoAdapter);
                    if(data.size()>0)
                    {
                        playAudioFile(data, 0);
                        updateBookmarkUI(data.get(0));
                        btn_prev_question.setVisibility(View.GONE);
                    }
                    addEvents(data);
                }));
                break;
            case ID_PART_THREE_TRAINING:
                DBQuery.loadTestNameList(testList -> {
                    DBQuery.loadDataPartThree(TrainingActivity.this,testList, questionList -> {
                        loadUI(ID_PART_THREE_TRAINING);
                        TrainingPartThreeAndFourAdapter partThreeAdapter = new TrainingPartThreeAndFourAdapter(TrainingActivity.this,questionList);
                        questionPager.setAdapter(partThreeAdapter);
                        if(questionList.size()>0)
                        {
                            playAudioFile(questionList, 0);
                            updateBookmarkUI(questionList.get(0));
                            btn_prev_question.setVisibility(View.GONE);
                        }
                        addEvents(questionList);
                    });
                });
                break;
            case ID_PART_FOUR_TRAINING:
                DBQuery.loadTestNameList(testList -> {
                    DBQuery.loadDataPartFour(TrainingActivity.this,testList, questionList -> {
                        loadUI(ID_PART_FOUR_TRAINING);
                        TrainingPartThreeAndFourAdapter partFourAdapter = new TrainingPartThreeAndFourAdapter(TrainingActivity.this,questionList);
                        questionPager.setAdapter(partFourAdapter);
                        if(questionList.size()>0)
                        {
                            playAudioFile(questionList, 0);
                            btn_prev_question.setVisibility(View.GONE);
                        }
                        addEvents(questionList);
                    });
                });
                break;
            case ID_REVIEW_TRAINING:
                List<Question> questionList = new ArrayList<>();
                       questionList.addAll(new ArrayList<>(localDataRef.getReviewPartOne()));
                       questionList.addAll(new ArrayList<>(localDataRef.getReviewPartTwo()));
                       questionList.addAll(new ArrayList<>(localDataRef.getReviewPartThreeAndFour()));
                RevisionAdapter revisionAdapter = new RevisionAdapter(TrainingActivity.this,questionList);
                questionPager.setAdapter(revisionAdapter);
                if(questionList.size()>0)
                {
                    playAudioFile(questionList, 0);
                    btn_prev_question.setVisibility(View.GONE);
                }
                addEvents(questionList);
                break;

        }
    }
    private <T extends Question> void playAudioFile(List<T> data, int position) {
        btn_play.setImageResource(R.drawable.ic_pause);
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(data.get(position).getAudio_url());
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                txt_timestamp.setText(Utils.getTimeString(mediaPlayer.getDuration()));
                slider.setValueTo(mediaPlayer.getDuration());
                mediaPlayer.start();
                updateSliderProgress();
            }
        });
        mediaPlayer.prepareAsync();
    }

    private void updateSliderProgress() {
        try {
            int currPos = mediaPlayer.getCurrentPosition();
            if(currPos<=slider.getValueTo())
            slider.setValue(currPos);
            runnable = () -> {
                try {
                    if(mediaPlayer.isPlaying())
                    {
                        btn_play.setImageResource(R.drawable.ic_pause);
                    }else {
                        btn_play.setImageResource(R.drawable.ic_play);
                    }
                    updateMediaTimeRemaining();
                    updateSliderProgress();
                }catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            };
            handler.postDelayed(runnable, 200);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateMediaTimeRemaining() {
        long timeRemain = mediaPlayer.getDuration() - mediaPlayer.getCurrentPosition();
        txt_timestamp.setText(Utils.getTimeString(timeRemain));
    }

    private void loadUI(final int part_ID) {
        switch (part_ID){
            case ID_PART_ONE_TRAINING:
                updateToolbarTitle(R.string.part1_training,1);
                break;
            case ID_PART_TWO_TRAINING:
                updateToolbarTitle(R.string.part2_training,2);
                break;
            case ID_PART_THREE_TRAINING:
                updateToolbarTitle(R.string.part3_training,3);
                break;
            case ID_PART_FOUR_TRAINING:
                updateToolbarTitle(R.string.part4_training,4);
                break;
        }
    }

    private void updateToolbarTitle(int titleId, int partId) {
        txt_toolbar_title.setText(getResources().getString(titleId));
        txt_toolbar_description.setText(CategoryAdapter.categories[partId-1].getDescription());
    }

    private void addControls() {
        slider = findViewById(R.id.slider);
        btn_play = findViewById(R.id.btn_play);
        btn_backward = findViewById(R.id.btn_backward);
        btn_forward = findViewById(R.id.btn_forward);
        btn_bookmark = findViewById(R.id.btn_bookmark);
        toolbar = findViewById(R.id.toolbar);
        txt_timestamp = findViewById(R.id.txt_timestamp);
        txt_toolbar_title = findViewById(R.id.txt_toolbar_title);
        txt_toolbar_description = findViewById(R.id.txt_toolbar_description);
        btn_show_script = findViewById(R.id.btn_show_script);
        btn_next_question = findViewById(R.id.btn_next_question);
        btn_prev_question = findViewById(R.id.btn_prev_question);
        loadingBar = findViewById(R.id.loadingBar);
        questionPager = findViewById(R.id.viewpager_training);
        initPositionButtons();
        handler = new Handler();
        localDataRef = LocalData.getInstance(TrainingActivity.this).statusDAO();
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(mediaPlayer!=null)
        {
            mediaPlayer.release();
        }
    }

    public AppCompatButton getBtnShowScript() {
        return btn_show_script;
    }
}