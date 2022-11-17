package com.peterwayne.toeic900.Model;

public class QuestionPartTwo extends Question{
    private String key;
    private String script_keyA, script_keyB,script_keyC;
    private String script;
    public QuestionPartTwo() {
        super();
    }

    public String getScript_keyA() {
        return script_keyA;
    }

    public void setScript_keyA(String script_keyA) {
        this.script_keyA = script_keyA;
    }

    public String getScript_keyB() {
        return script_keyB;
    }

    public void setScript_keyB(String script_keyB) {
        this.script_keyB = script_keyB;
    }

    public String getScript_keyC() {
        return script_keyC;
    }

    public void setScript_keyC(String script_keyC) {
        this.script_keyC = script_keyC;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


}
