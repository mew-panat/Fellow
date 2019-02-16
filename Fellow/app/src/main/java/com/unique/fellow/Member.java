package com.unique.fellow;

public class Member {
    private String userID;

    public Member(){

    }

    public Member(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
