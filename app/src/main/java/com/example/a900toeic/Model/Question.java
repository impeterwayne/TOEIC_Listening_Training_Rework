package com.example.a900toeic.Model;

public class Question {
    private String id;
    private String audio_url;
    private String key;
    private String script;

    public Question(String id, String audio_url, String key, String script) {
        this.id = id;
        this.audio_url = audio_url;
        this.key = key;
        this.script = script;
    }

    public Question() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAudio_url() {
        return audio_url;
    }

    public void setAudio_url(String audio_url) {
        this.audio_url = audio_url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }
}
