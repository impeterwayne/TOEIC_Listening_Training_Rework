package com.peterwayne.toeic900.Fragment;


import static com.peterwayne.toeic900.Utils.Utils.getCurrentDayOfWeek;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.peterwayne.toeic900.Database.DBQuery;
import com.peterwayne.toeic900.R;
import java.util.ArrayList;
import java.util.List;

public class StatisticFragment extends Fragment {
    private LineChart mChart;
    private String[] dayOfWeekArr;
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
        initDayOfWeekArr();

    }
    private void initDayOfWeekArr() {
        dayOfWeekArr = new String[7];
        int step = 7 - getCurrentDayOfWeek();
        String[] week = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri","Sat"};
        for(int i = 0; i<7; i++)
        {
            int temp = i-step;
            if(temp<0)
            {
                temp = temp+7;
            }
            dayOfWeekArr[i] = week[temp];
        }
    }


    private void setUpTheChart(List<Integer> dataStatistic){
        mChart.setNoDataText("No data");
        mChart.getDescription().setText("");
        // legend
        Legend legend = mChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setWordWrapEnabled(true);
        legend.setTextSize(12);

        YAxis yAxisRight = mChart.getAxisRight();
        YAxis yAxisLeft = mChart.getAxisLeft();
        XAxis xAxis = mChart.getXAxis();
        // remove axis

        xAxis.setDrawGridLines(false);
        yAxisRight.setEnabled(false);
        //custom axis
        yAxisLeft.setTextSize(12);
        yAxisLeft.setAxisLineColor(Color.TRANSPARENT);
        yAxisLeft.setAxisMaximum(18);
        yAxisLeft.setAxisMinimum(-2);

        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(1f);
        xAxis.setTextSize(12);
        xAxis.setAxisLineColor(Color.TRANSPARENT);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return dayOfWeekArr[(int) value];
            }
        });
        //data line
        LineDataSet lineDataSet1 = new LineDataSet(loadEntryScore(dataStatistic), "Practice");
//        lineDataSet1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
//        lineDataSet1.setCubicIntensity(0.1f);
        lineDataSet1.setHighlightEnabled(true);
        lineDataSet1.setLineWidth(8);
        lineDataSet1.setDrawCircles(true);
        lineDataSet1.setDrawCircleHole(true);
        lineDataSet1.setCircleHoleRadius(6);
        lineDataSet1.setCircleRadius(10);
        lineDataSet1.setCircleColor(Color.GREEN);
        lineDataSet1.setColor(getResources().getColor(R.color.primary));
        lineDataSet1.setValueTextSize(18);
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

    private ArrayList<Entry> loadEntryScore(List<Integer> dataStatistic)
    {
        ArrayList<Entry> data = new ArrayList<>();
        for (int i = 0; i < dataStatistic.size(); i++) {
            data.add(new Entry((float)i, (float) dataStatistic.get(i)));
        }
        return data;
    }

    @Override
    public void onResume() {
        super.onResume();
        DBQuery.loadStatisticData(new DBQuery.iStatisticsCallback() {
            @Override
            public void onCallBack(List<Integer> dataStatistic) {
                setUpTheChart(dataStatistic);
            }
        });
    }
}