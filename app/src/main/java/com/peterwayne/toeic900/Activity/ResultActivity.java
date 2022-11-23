package com.peterwayne.toeic900.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.peterwayne.toeic900.Adapter.AnswerSheetResultAdapter;
import com.peterwayne.toeic900.Database.DBQuery;
import com.peterwayne.toeic900.Model.Answer;
import com.peterwayne.toeic900.Model.Question;
import com.peterwayne.toeic900.R;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;
import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {
    private List<Answer> answerList;
    private List<Question> questionList;
    private PieChart resultChart;
    private TickerView tickerView;
    private Toolbar toolbar;
    private RecyclerView rcv_answer_sheet_result;
    private AnswerSheetResultAdapter answerSheetResultAdapter;
    private TextView btn_done;
    private long numCorrect,numIncorrect,numInComplete,score;
    private final int[] colorClassArray = new int[]{Color.RED, Color.GREEN, Color.GRAY};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        initData();
        calculateCorrectAnswer();
        calculateScore();
        addControls();
        drawChart();
        initAnswerSheet();
        addEvents();
        DBQuery.updateTestStatistics();
    }

    private void initAnswerSheet() {
        GridLayoutManager layoutManager = new GridLayoutManager(ResultActivity.this,4,
                LinearLayoutManager.VERTICAL,false);
        answerSheetResultAdapter = new AnswerSheetResultAdapter(ResultActivity.this,answerList);
        rcv_answer_sheet_result.setLayoutManager(layoutManager);
        rcv_answer_sheet_result.setAdapter(answerSheetResultAdapter);
    }

    private void addEvents() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void initData() {
        numCorrect = 0;
        numIncorrect = 0;
        numInComplete = 0;
        score =0;
        answerList = (List<Answer>) getIntent().getSerializableExtra("answerList");
        questionList = (List<Question>) getIntent().getSerializableExtra("questionList");
    }

    private void addControls() {
        resultChart = findViewById(R.id.chart_result);
        tickerView = findViewById(R.id.ticker);
        toolbar = findViewById(R.id.toolbar);
        btn_done = findViewById(R.id.btn_done);
        rcv_answer_sheet_result = findViewById(R.id.rcv_answer_sheet_result);
    }
    private void drawChart() {
        tickerView.setCharacterLists(TickerUtils.provideNumberList());
        tickerView.setPreferredScrollingDirection(TickerView.ScrollingDirection.ANY);
        resultChart.animateX(2000);
        tickerView.setText("0");
        tickerView.setText(score +"");
        resultChart.setTouchEnabled(false);
        resultChart.getDescription().setText("");
        resultChart.getLegend().setEnabled(false);
        PieDataSet pieDataSet = new PieDataSet(loadDataValues(), "");
        pieDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if(value ==0) return "";
                return String.valueOf((int) value);
            }
        });
        pieDataSet.setValueTextSize(18);
        pieDataSet.setColors(colorClassArray);
        PieData pieData = new PieData(pieDataSet);
        resultChart.setData(pieData);
    }
    private List<PieEntry> loadDataValues() {
        ArrayList<PieEntry> res = new ArrayList<>();
        res.add(new PieEntry(numIncorrect, ""));
        res.add(new PieEntry(numCorrect, ""));
        res.add(new PieEntry(numInComplete, ""));
        return res;
    }
    private void calculateCorrectAnswer()
    {
        for (Answer answer : answerList) {
            if(answer.getKeyChosen()==null)
            {
                numInComplete++;
            }else
            {
                if(answer.getKeyChosen().equals(answer.getKey())) numCorrect++;
                else numIncorrect++;
            }
        }
    }
    private void calculateScore() {
        // conversion table at: shorturl.at/copuE
        if(numCorrect>=0&&numCorrect<=6) score = 5;
        else if(numCorrect>=7&&numCorrect<=30)
        {
            score = 10 + (5* (numCorrect-7));
        }else if( numCorrect >=31&&numCorrect<=38)
        {
            score = 135 + (5* (numCorrect-31));
        }else if (numCorrect >=39&&numCorrect<=43)
        {
            score = 180 + (5* (numCorrect-39));
        }else if(numCorrect == 44 )
        {
            score = 210;
        } else if (numCorrect>=45&&numCorrect<=53)
        {
            score = 220 + (5* (numCorrect-45));
        } else if (numCorrect>=54&&numCorrect<=69)
        {
            score = 270 + (5* (numCorrect-54));
        }else if (numCorrect>=70&&numCorrect<=74)
        {
            score = 360 + (5* (numCorrect-70));
        }else if (numCorrect>=75&&numCorrect<=79)
        {
            score = 390 + (5* (numCorrect-75));
        }else if (numCorrect>=80 && numCorrect<=84)
        {
            score = 420 + (5* (numCorrect-80));
        }else if (numCorrect>=85&&numCorrect<=87)
        {
            score = 450 + (5* (numCorrect-85));
        }else if (numCorrect>=88&&numCorrect<=92)
        {
            score = 470 + (5* (numCorrect-88));
        }else if (numCorrect>=92&&numCorrect<=100)
        {
            score = 495;
        }
    }

    public List<Question> getQuestionList() {
        return questionList;
    }
}
