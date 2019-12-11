package com.example.buffstuff.Chat;

import java.util.Date;

// Messages object used to display information about the messages
public class Messages {

    String sender, text;
    Date sentAt;
    Boolean self;

    public Messages() {

    }

    public Messages(String sender, String text, Date sentAt) {
        this.sender = sender;
        this.text = text;
        this.sentAt = sentAt;
    }

    public Boolean getIsSenderSelf() { return self; }

    public void setIsSenderSelf(Boolean self) { this.self = self; }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getSentAt() {
        return sentAt;
    }

    public void setSentAt(Date sentAt) {
        this.sentAt = sentAt;
    }

}
