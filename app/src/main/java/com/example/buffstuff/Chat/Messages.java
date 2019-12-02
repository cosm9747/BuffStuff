package com.example.buffstuff.Chat;

import java.util.Date;

public class Messages {

    String sender, text, type;
    Date time;

    public Messages() {

    }

    public Messages(String sender, String text, Date time) {
        this.sender = sender;
        this.text = text;
        this.time = time;
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getType() {return type;}

    public void setType(String type) { this.type = type;}

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
