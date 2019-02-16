package com.unique.fellow;

public class Note {
    private String noteID;
    private String note;
    private int noteType; // 1: text, 2: picture
    private String messageTime;
    private String userID;

    public Note(String note, int noteType, String messageTime, String userID) {
        this.note = note;
        this.noteType = noteType;
        this.messageTime = messageTime;
        this.userID = userID;
    }

    public String getNoteID() {
        return noteID;
    }

    public void setNoteID(String noteID) {
        this.noteID = noteID;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getNoteType() {
        return noteType;
    }

    public void setNoteType(int noteType) {
        this.noteType = noteType;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
