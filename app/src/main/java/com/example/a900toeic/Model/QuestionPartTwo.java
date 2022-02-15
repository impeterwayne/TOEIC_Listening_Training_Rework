package com.example.a900toeic.Model;

public class QuestionPartTwo extends Question{
    private String keyA, keyB,keyC;

    public QuestionPartTwo() {
    }

    public QuestionPartTwo(String id, String audio_url, String key, String script, String keyA, String keyB, String keyC) {
        super(id, audio_url, key, script);
        this.keyA = keyA;
        this.keyB = keyB;
        this.keyC = keyC;
    }

    public String getKeyA() {
        return keyA;
    }

    public void setKeyA(String keyA) {
        this.keyA = keyA;
    }

    public String getKeyB() {
        return keyB;
    }

    public void setKeyB(String keyB) {
        this.keyB = keyB;
    }

    public String getKeyC() {
        return keyC;
    }

    public void setKeyC(String keyC) {
        this.keyC = keyC;
    }
}
