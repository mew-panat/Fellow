package com.unique.fellow;

import android.net.Uri;

import java.util.List;

public class UserCollections {
    private String userID;
    private String DOB;
    private String email;
    private String name;
    private String userType;
    private String profileURL;

    public UserCollections(){

    }

    public UserCollections(String DOB, String email, String name, String profileURL, String userType) {
        this.DOB = DOB;
        this.email = email;
        this.name = name;
        this.profileURL = profileURL;
        this.userType = userType;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
    }


    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getname() {
        return name;
    }

    public void setfName(String name) {
        this.name = name;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
