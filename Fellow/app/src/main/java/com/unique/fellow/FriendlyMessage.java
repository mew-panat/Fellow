package com.unique.fellow;

public class FriendlyMessage {

    private String messageID;
    private String message;
    private String userID;
    private String photoUrl;
    private String imageUrl;

    public FriendlyMessage(){

    }

    public FriendlyMessage(String message, String userID, String photoUrl, String imageUrl) {
        this.message = message;
        this.userID = userID;
        this.photoUrl = photoUrl;
        this.imageUrl = imageUrl;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
