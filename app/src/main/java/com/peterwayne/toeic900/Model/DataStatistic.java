package com.peterwayne.toeic900.Model;

public class DataStatistic {
    private int score;
    private String testName;
    private String date;
    private int time;
    public DataStatistic() {
    }

    public DataStatistic(int time , int score, String testName, String date ) {
        this.score = score;
        this.testName = testName;
        this.date = date;
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }
}
