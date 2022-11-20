package com.peterwayne.toeic900.Model;

import java.io.Serializable;

public class Answer implements Serializable {
    private long number;
    private String key;
    private String keyChosen;

    public Answer(final long number, final String key)
    {
        this.number = number;
        this.key = key;
    }

    public long getNumber() {
        return number;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKeyChosen() {
        return keyChosen;
    }

    public void setKeyChosen(String keyChosen) {
        this.keyChosen = keyChosen;
    }
}
