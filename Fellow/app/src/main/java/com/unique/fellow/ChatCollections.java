package com.unique.fellow;

import java.util.List;

public class ChatCollections {
    private String chatName;
    private String chatRoomID;
    private String date;
    List<Member> members;
    List<FriendlyMessage> messages;
    List<Note> notes;

    public ChatCollections(String chatName, String date, List<Member> member, List<FriendlyMessage> message, List<Note> note) {
        this.chatName = chatName;
        this.date = date;
        this.members = member;
        this.messages = message;
        this.notes = note;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public String getChatRoomID() {
        return chatRoomID;
    }

    public void setChatRoomID(String chatRoomID) {
        this.chatRoomID = chatRoomID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Member> getMember() {
        return members;
    }

    public void setMember(List<Member> member) {
        this.members = member;
    }

    public List<FriendlyMessage> getMessage() {
        return messages;
    }

    public void setMessage(List<FriendlyMessage> message) {
        this.messages = message;
    }

    public List<Note> getNote() {
        return notes;
    }

    public void setNote(List<Note> note) {
        this.notes = note;
    }
}
