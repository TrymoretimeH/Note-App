package com.example.noteapp.model;

import com.google.firebase.Timestamp;

public class PassNote {
    private String title, pass;
    private Timestamp timestamp;

    public PassNote() {
    }

    public PassNote(String title, String pass) {
        this.title = title;
        this.pass = pass;
    }

    public PassNote(String title, String pass, Timestamp timestamp) {
        this.title = title;
        this.pass = pass;
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
