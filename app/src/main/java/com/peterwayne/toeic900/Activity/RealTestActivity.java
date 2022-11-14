package com.peterwayne.toeic900.Activity;

import static com.peterwayne.toeic900.Utils.Utils.getTimeString;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.Slider;
import com.peterwayne.toeic900.Adapter.AnswerSheetAdapter;
import com.peterwayne.toeic900.Adapter.RealTestAdapter;
import com.peterwayne.toeic900.Database.DBQuery;
import com.peterwayne.toeic900.Model.Answer;
import com.peterwayne.toeic900.Model.Question;
import com.peterwayne.toeic900.Model.QuestionPartThreeAndFour;
import com.peterwayne.toeic900.R;
import com.peterwayne.toeic900.Utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RealTestActivity extends AppCompatActivity {
    private TextView txt_toolbar_title, txt_timestamp;
    private ImageView btn_question_navigation, btn_play,btn_prev_question, btn_next_question;
    private DrawerLayout drawer_layout;
    private Slider slider;
    private NavigationView nav_questions;
    private MediaPlayer mediaPlayer;
    private Runnable runnable;
    private Handler handler;
    private ViewPager2 testPager;
    private View headerLayout;
    private RecyclerView rcv_test_answer;
    private List<Answer> answerList;
    private List<Question> questionData;
    private AnswerSheetAdapter answerSheetAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_test);
        addControls();
        updateToolbarTitle();
        playAudioFile();
        addEvents();
        DBQuery.loadTestQuestions(getIntent().getStringExtra("testName"), new DBQuery.iTestQuestionCallback() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCallBack(List<Question> questionList) {
                questionList.sort(new Comparator<Question>() {
                    @Override
                    public int compare(Question t1, Question t2) {
                        return (int)(t1.getNumber()-t2.getNumber());
                    }
                });
                questionData = questionList;
                RealTestAdapter testAdapter = new RealTestAdapter(RealTestActivity.this, questionList);
                testPager.setAdapter(testAdapter);
                generateAnswerNavigation(questionList);
                GridLayoutManager layoutManager = new GridLayoutManager(RealTestActivity.this,3,
                        LinearLayoutManager.VERTICAL,false);
                answerSheetAdapter = new AnswerSheetAdapter(RealTestActivity.this, answerList);
                rcv_test_answer.setLayoutManager(layoutManager);
                rcv_test_answer.setAdapter(answerSheetAdapter);
            }
        });
    }

    private void generateAnswerNavigation(List<Question> questionList) {
        answerList = new ArrayList<>();
        for(final Question question : questionList)
        {
            Answer answer = new Answer(question.getNumber());
            answerList.add(answer);
            if(question instanceof QuestionPartThreeAndFour)
            {
                answerList.add(new Answer(((QuestionPartThreeAndFour) question).getNumber2()));
                answerList.add(new Answer(((QuestionPartThreeAndFour) question).getNumber3()));
            }
        }
    }

    private void addEvents() {
        btn_prev_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currPos = testPager.getCurrentItem();
                testPager.setCurrentItem(currPos-1);
            }
        });
        btn_next_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currPos = testPager.getCurrentItem();
                testPager.setCurrentItem(currPos+1);
            }
        });
        btn_question_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer_layout.openDrawer(GravityCompat.END);
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
        testPager.setOffscreenPageLimit(2);
    }

    private void addControls() {
        drawer_layout = findViewById(R.id.drawer_layout);
        nav_questions = findViewById(R.id.nav_questions);
        txt_toolbar_title = findViewById(R.id.txt_toolbar_title);
        txt_timestamp = findViewById(R.id.txt_timestamp);
        testPager = findViewById(R.id.viewpager_test);
        btn_question_navigation = findViewById(R.id.btn_question_navigation);
        btn_play = findViewById(R.id.btn_play);
        btn_prev_question = findViewById(R.id.btn_prev_question);
        btn_next_question = findViewById(R.id.btn_next_question);
        slider = findViewById(R.id.slider);
        handler = new Handler();
        headerLayout = nav_questions.getHeaderView(0);
        rcv_test_answer = headerLayout.findViewById(R.id.rcv_test_answer);

    }
    private void updateToolbarTitle()
    {
        txt_toolbar_title.setText(getIntent().getStringExtra("testName"));
    }
    private void playAudioFile()
    {
        DBQuery.loadTestAudio(getIntent().getStringExtra("testName"), new DBQuery.iAudioCallback() {
            @Override
            public void onCallBack(String audio_url) {
                btn_play.setImageResource(R.drawable.ic_pause);
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(audio_url);
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
        });
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

    public ViewPager2 getTestPager() {
        return testPager;
    }

    public List<Question> getQuestionData() {
        return questionData;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public AnswerSheetAdapter getAnswerSheetAdapter() {
        return answerSheetAdapter;
    }
}