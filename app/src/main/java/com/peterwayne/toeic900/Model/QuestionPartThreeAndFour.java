package com.peterwayne.toeic900.Model;

public class QuestionPartThreeAndFour extends Question{
    private String key1, key2, key3;
    private String question1;
    private String question2;
    private String question3;
    private String script_key1A,script_key1B,script_key1C,script_key1D;
    private String script_key2A,script_key2B,script_key2C,script_key2D;
    private String script_key3A,script_key3B,script_key3C,script_key3D;
    private String image_url;
    private String script;
    private long number1, number2, number3;
    public QuestionPartThreeAndFour() {
        super();
    }

    public String getKey1() {
        return key1;
    }

    public void setKey1(String key1) {
        this.key1 = key1;
    }

    public String getKey2() {
        return key2;
    }

    public void setKey2(String key2) {
        this.key2 = key2;
    }

    public String getKey3() {
        return key3;
    }

    public void setKey3(String key3) {
        this.key3 = key3;
    }

    public String getQuestion1() {
        return question1;
    }

    public void setQuestion1(String question1) {
        this.question1 = question1;
    }

    public String getQuestion2() {
        return question2;
    }

    public void setQuestion2(String question2) {
        this.question2 = question2;
    }

    public String getQuestion3() {
        return question3;
    }

    public void setQuestion3(String question3) {
        this.question3 = question3;
    }

    public String getScript_key1A() {
        return script_key1A;
    }

    public void setScript_key1A(String script_key1A) {
        this.script_key1A = script_key1A;
    }

    public String getScript_key1B() {
        return script_key1B;
    }

    public void setScript_key1B(String script_key1B) {
        this.script_key1B = script_key1B;
    }

    public String getScript_key1C() {
        return script_key1C;
    }

    public void setScript_key1C(String script_key1C) {
        this.script_key1C = script_key1C;
    }

    public String getScript_key1D() {
        return script_key1D;
    }

    public void setScript_key1D(String script_key1D) {
        this.script_key1D = script_key1D;
    }

    public String getScript_key2A() {
        return script_key2A;
    }

    public void setScript_key2A(String script_key2A) {
        this.script_key2A = script_key2A;
    }

    public String getScript_key2B() {
        return script_key2B;
    }

    public void setScript_key2B(String script_key2B) {
        this.script_key2B = script_key2B;
    }

    public String getScript_key2C() {
        return script_key2C;
    }

    public void setScript_key2C(String script_key2C) {
        this.script_key2C = script_key2C;
    }

    public String getScript_key2D() {
        return script_key2D;
    }

    public void setScript_key2D(String script_key2D) {
        this.script_key2D = script_key2D;
    }

    public String getScript_key3A() {
        return script_key3A;
    }

    public void setScript_key3A(String script_key3A) {
        this.script_key3A = script_key3A;
    }

    public String getScript_key3B() {
        return script_key3B;
    }

    public void setScript_key3B(String script_key3B) {
        this.script_key3B = script_key3B;
    }

    public String getScript_key3C() {
        return script_key3C;
    }

    public void setScript_key3C(String script_key3C) {
        this.script_key3C = script_key3C;
    }

    public String getScript_key3D() {
        return script_key3D;
    }

    public void setScript_key3D(String script_key3D) {
        this.script_key3D = script_key3D;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    @Override
    public long getNumber() {
        return getNumber1();
    }

    public long getNumber1() {
        return number1;
    }


    public long getNumber2() {
        return number2;
    }


    public long getNumber3() {
        return number3;
    }

}
