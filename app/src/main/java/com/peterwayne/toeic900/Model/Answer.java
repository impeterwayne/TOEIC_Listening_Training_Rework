package com.peterwayne.toeic900.Model;

public class Answer{
    private long number;
    private String key;
    private String keyChoosed;

    public Answer(final long number)
    {
        this.number = number;
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

    public String getKeyChoosed() {
        return keyChoosed;
    }

    public void setKeyChoosed(String keyChoosed) {
        this.keyChoosed = keyChoosed;
    }
}
