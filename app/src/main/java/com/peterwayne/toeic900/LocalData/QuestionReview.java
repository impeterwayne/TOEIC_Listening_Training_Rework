package com.peterwayne.toeic900.LocalData;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "revision")
public class QuestionReview {
    @PrimaryKey(autoGenerate = true)
    private int order;
    private String id;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
