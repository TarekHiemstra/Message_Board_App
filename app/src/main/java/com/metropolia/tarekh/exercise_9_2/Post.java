package com.metropolia.tarekh.exercise_9_2;

public class Post {
    private String userID;
    private String displayName;
    private String timestamp;
    public String text;

    public Post() {
    }

    public Post(String userID, String displayName, String timestamp, String text) {
        this.userID = userID;
        this.displayName = displayName;
        this.timestamp = timestamp;
        this.text = text;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
