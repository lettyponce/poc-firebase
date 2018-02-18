package com.example.lfuzzatti.poc1.model;

public class Message {

    private String message;
    private String owner;
    private String photoUrl;

    public Message() {
    }

    public Message(String message, String owner, String photoUrl) {
        this.message = message;
        this.owner = owner;
        this.photoUrl = photoUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                ", owner='" + owner + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                '}';
    }
}
