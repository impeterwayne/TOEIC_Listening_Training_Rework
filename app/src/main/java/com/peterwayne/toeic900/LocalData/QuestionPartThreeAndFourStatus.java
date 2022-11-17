package com.peterwayne.toeic900.LocalData;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.peterwayne.toeic900.Model.QuestionPartThreeAndFour;
@Entity(tableName = "statusPartThreeAndFour")
public class QuestionPartThreeAndFourStatus extends QuestionPartThreeAndFour {
    @PrimaryKey(autoGenerate = true)
    private int order;
    private boolean done;
    private boolean toReview;

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isToReview() {
        return toReview;
    }

    public void setToReview(boolean toReview) {
        this.toReview = toReview;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
