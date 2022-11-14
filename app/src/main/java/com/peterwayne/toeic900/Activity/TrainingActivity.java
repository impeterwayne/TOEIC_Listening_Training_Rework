package com.peterwayne.toeic900.Activity;
import static com.peterwayne.toeic900.Utils.Utils.ID_PART_FOUR_TRAINING;
import static com.peterwayne.toeic900.Utils.Utils.ID_PART_ONE_TRAINING;
import static com.peterwayne.toeic900.Utils.Utils.ID_PART_THREE_TRAINING;
import static com.peterwayne.toeic900.Utils.Utils.ID_PART_TWO_TRAINING;
import static com.peterwayne.toeic900.Utils.Utils.PART_ID;
import static com.peterwayne.toeic900.Utils.Utils.getTimeString;
import android.app.Dialog;
import android.content.Intent;
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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.Slider;
import com.peterwayne.toeic900.Adapter.CategoryAdapter;
import com.peterwayne.toeic900.Adapter.TrainingPartOneAdapter;
import com.peterwayne.toeic900.Adapter.TrainingPartThreeAndFourAdapter;
import com.peterwayne.toeic900.Adapter.TrainingPartTwoAdapter;
import com.peterwayne.toeic900.Database.DBQuery;
import com.peterwayne.toeic900.LocalData.DataLocalManager;
import com.peterwayne.toeic900.Model.Question;
import com.peterwayne.toeic900.Model.QuestionPartOne;
import com.peterwayne.toeic900.Model.QuestionPartThreeAndFour;
import com.peterwayne.toeic900.Model.QuestionPartTwo;
import com.peterwayne.toeic900.R;
import com.peterwayne.toeic900.Utils.Utils;
import java.io.IOException;
import java.util.List;

public class TrainingActivity extends AppCompatActivity {
    private Slider slider;
    private ImageView btn_backward, btn_forward,btn_play, btn_bookmark;
    private ImageView btn_next_question, btn_prev_question;
    private Toolbar toolbar;
    private TextView txt_toolbar_title,txt_toolbar_description;
    private TextView txt_timestamp;
    private ViewPager2 mViewPager;
    private MediaPlayer mediaPlayer;
    private ProgressBar loadingBar;
    private AppCompatButton btn_show_script;
    private int partId;
    private Runnable runnable;
    private Handler handler;
    private final int[] positionArr = { R.id.btn_question_one, R.id.btn_question_two, R.id.btn_question_three,
                                        R.id.btn_question_four,R.id.btn_question_five};
    private ImageView[] positionButtons;

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
                    mViewPager.setCurrentItem(currPos);
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
                startActivity(new Intent(TrainingActivity.this, MainActivity.class));
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
            mViewPager.setOffscreenPageLimit(2);
            mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    updateQuestionNavigationUI(position);
                    updateQuestionNumberUI(position);
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

                private void updateQuestionNavigationUI(final int position) {
                    if(position==0) btn_prev_question.setVisibility(View.GONE);
                    if(position==data.size()-1) btn_next_question.setVisibility(View.GONE);
                    if(position>0) btn_prev_question.setVisibility(View.VISIBLE);
                    if(position<data.size()-1) btn_next_question.setVisibility(View.VISIBLE);
                }
            });
            btn_prev_question.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int currPos = mViewPager.getCurrentItem();
                    mViewPager.setCurrentItem(currPos-1);
                }
            });
            btn_next_question.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int currPos = mViewPager.getCurrentItem();
                    mViewPager.setCurrentItem(currPos+1);
                }
            });
            btn_show_script.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showScriptDialog(data.get(mViewPager.getCurrentItem()));
                }
            });
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
                DBQuery.loadTestNameList(testList -> DBQuery.loadDataPartOne(testList, data -> {
                    loadUI(ID_PART_ONE_TRAINING);
                    TrainingPartOneAdapter mTrainingPartOneAdapter = new TrainingPartOneAdapter(TrainingActivity.this, data);
                    mViewPager.setAdapter(mTrainingPartOneAdapter);
                    if(data.size()>0)
                    {
                        playAudioFile(data, 0);
                        btn_prev_question.setVisibility(View.GONE);
                    }
                    addEvents(data);
                }));

                break;
            case ID_PART_TWO_TRAINING:
                DBQuery.loadTestNameList(testList -> DBQuery.loadDataPartTwo(testList, data -> {
                    loadUI(ID_PART_TWO_TRAINING);
                    TrainingPartTwoAdapter mTrainingPartTwoAdapter = new TrainingPartTwoAdapter(TrainingActivity.this, data);
                    mViewPager.setAdapter(mTrainingPartTwoAdapter);
                    if(data.size()>0)
                    {
                        playAudioFile(data, 0);
                        btn_prev_question.setVisibility(View.GONE);
                    }
                    addEvents(data);
                }));
                break;
            case ID_PART_THREE_TRAINING:
                DBQuery.loadTestNameList(testList -> {
                    DBQuery.loadDataPartThree(testList, data -> {
                        loadUI(ID_PART_THREE_TRAINING);
                        TrainingPartThreeAndFourAdapter partThreeAdapter = new TrainingPartThreeAndFourAdapter(TrainingActivity.this,data);
                        mViewPager.setAdapter(partThreeAdapter);
                        if(data.size()>0)
                        {
                            playAudioFile(data, 0);
                            btn_prev_question.setVisibility(View.GONE);
                        }
                        addEvents(data);
                    });
                });
                break;
            case ID_PART_FOUR_TRAINING:
                DBQuery.loadTestNameList(testList -> {
                    DBQuery.loadDataPartFour(testList, data -> {
                        loadUI(ID_PART_FOUR_TRAINING);
                        TrainingPartThreeAndFourAdapter partFourAdapter = new TrainingPartThreeAndFourAdapter(TrainingActivity.this,data);
                        mViewPager.setAdapter(partFourAdapter);
                        if(data.size()>0)
                        {
                            playAudioFile(data, 0);
                            btn_prev_question.setVisibility(View.GONE);
                        }
                        addEvents(data);
                    });
                });
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
                slider.setValueTo(mediaPlayer.getDuration());
                txt_timestamp.setText(Utils.getTimeString(mediaPlayer.getDuration()));
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
                }catch (Exception ex)
                {
                    ex.printStackTrace();
                }
                updateSliderProgress();
            };
            handler.postDelayed(runnable, 200);
        }catch (Exception e) {
            e.printStackTrace();
        }
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
        mViewPager = findViewById(R.id.viewpager_training);
        initPositionButtons();
        handler = new Handler();
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