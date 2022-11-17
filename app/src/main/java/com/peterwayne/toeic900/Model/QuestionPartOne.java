package com.peterwayne.toeic900.Model;

public class QuestionPartOne extends Question{
    private String image_url;
    private String script_keyA;
    private String script_keyB;
    private String script_keyC;
    private String script_keyD;
    private String key;
    public QuestionPartOne() {
        super();
    }

    public String getImage_url() {
        return image_url;
    }

    public String getScript_keyA() {
        return script_keyA;
    }

    public String getScript_keyB() {
        return script_keyB;
    }

    public String getScript_keyC() {
        return script_keyC;
    }

    public String getScript_keyD() {
        return script_keyD;
    }

    public String getKey() {
        return key;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setScript_keyA(String script_keyA) {
        this.script_keyA = script_keyA;
    }

    public void setScript_keyB(String script_keyB) {
        this.script_keyB = script_keyB;
    }

    public void setScript_keyC(String script_keyC) {
        this.script_keyC = script_keyC;
    }

    public void setScript_keyD(String script_keyD) {
        this.script_keyD = script_keyD;
    }

    public void setKey(String key) {
        this.key = key;
    }
}



