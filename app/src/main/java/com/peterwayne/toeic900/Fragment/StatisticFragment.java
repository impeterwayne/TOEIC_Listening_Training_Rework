package com.peterwayne.toeic900.Fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.peterwayne.toeic900.Database.DBQuery;
import com.peterwayne.toeic900.Model.DataStatistic;
import com.peterwayne.toeic900.Model.LableMakerView;
import com.peterwayne.toeic900.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class StatisticFragment extends Fragment {
    private LineChart mChart;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_statistic, container, false);
        addControls(view);
        return view;
    }

    private void addControls(View view) {

        mChart = view.findViewById(R.id.mChart);
//        DBQuery.loadDataStatistic(new DBQuery.iDataStatisticCallback() {
//            @Override
//            public void onCallBack(ArrayList<DataStatistic> res) {
//                dataStatisticArr = res;
//                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM");
//                setUpTheChart();
//            }
//        });

    }
    private void setUpTheChart() {
        mChart.setNoDataText("No data");
        mChart.getDescription().setText("");
        mChart.getLegend().setEnabled(false);
        IMarker marker = new LableMakerView(this.getContext(), R.layout.label_chart);
        mChart.setMarker(marker);
        YAxis yAxisRight = mChart.getAxisRight();
        YAxis yAxisLeft = mChart.getAxisLeft();
        XAxis xAxis = mChart.getXAxis();
        // remove axis
        yAxisRight.setEnabled(false);
        yAxisRight.setDrawGridLines(false);
        yAxisLeft.setEnabled(false);
        yAxisLeft.setDrawGridLines(false);
        xAxis.setDrawGridLines(false);
        xAxis.setEnabled(false);
        //custom axis
        yAxisLeft.setAxisMinimum(0);
        yAxisLeft.setAxisLineWidth(5);
        yAxisLeft.setAxisLineColor(Color.BLACK);
        yAxisLeft.setAxisMaximum(550);



        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(1f);
        xAxis.setAxisLineWidth(5);
        xAxis.setAxisLineColor(Color.BLACK);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) value);
            }
        });
        //data line
        LineDataSet lineDataSet1 = new LineDataSet(loadEntryScore(), "Score");
        lineDataSet1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet1.setCubicIntensity(0.1f);
        lineDataSet1.setLineWidth(4);
        lineDataSet1.setColor(Color.GRAY);
        lineDataSet1.setValueTextSize(10);
        lineDataSet1.setDrawFilled(true);
        lineDataSet1.setFillDrawable(getResources().getDrawable(R.drawable.bg_gradient_chart));
        lineDataSet1.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) value);
            }
        });
        //set of line for chart
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);
        LineData data = new LineData(dataSets);
        mChart.setData(data);
        mChart.invalidate();
    }

    private ArrayList<Entry> loadEntryScore()
    {
//        ArrayList<Entry> data = new ArrayList<Entry>();
//        for(int i = 0; i< DBQuery.dataStatisticArr.size(); i++)
//        {
//            data.add(new Entry(DBQuery.dataStatisticArr.get(i).getTime(),DBQuery.dataStatisticArr.get(i).getScore()));
//        }
//        return data;
        return null;
    }
}