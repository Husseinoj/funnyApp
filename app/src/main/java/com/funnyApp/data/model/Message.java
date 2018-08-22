package com.funnyApp.data.model;

/**
 * Created by android on 5/26/17.
 */

public class Message {
    public Message(String title) {
        this.title = title;
    }
    public Message(){}
    private int messageStatus;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(int messageStatus) {
        this.messageStatus = messageStatus;
    }
}
