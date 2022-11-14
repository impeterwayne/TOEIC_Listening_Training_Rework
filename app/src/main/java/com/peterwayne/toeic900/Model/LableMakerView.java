package com.peterwayne.toeic900.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.peterwayne.toeic900.Database.DBQuery;
import com.peterwayne.toeic900.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

public class LableMakerView extends MarkerView {
    private TextView txt_test_name, txt_date;
    public LableMakerView(Context context, int layoutResource) {
        super(context, layoutResource);
        View view = LayoutInflater.from(context).inflate(layoutResource, this);

        txt_test_name = view.findViewById(R.id.txt_test_name);
        txt_date = view.findViewById(R.id.txt_date);
    }
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        int position = (int) e.getX();
//        txt_date.setText(DBQuery.dataStatisticArr.get(position-1).getDate());
//        txt_test_name.setText(DBQuery.dataStatisticArr.get(position-1).getTestName());

        super.refreshContent(e, highlight);
    }

    private MPPointF mOffset;

    @Override
    public MPPointF getOffset() {

        if(mOffset == null) {
            mOffset = new MPPointF(-(getWidth()), -getHeight());
        }

        return mOffset;
    }
}
