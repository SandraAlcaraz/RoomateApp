package com.example.sandra.roomate_app;

import java.text.DateFormat;

public class Announcement {
    private String id, groupId, content;
    private int createdBy;
    private DateFormat createdAt, updatedAt;

    public Announcement(String id, String groupId, String content, int createdBy, DateFormat createdAt){
        this.id = id;
        this.groupId = groupId;
        this.content = content;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    public String getId(){
        return this.id;
    }

    public String getGroupId(){
        return this.groupId;
    }

    public String getContent(){
        return this.content;
    }

    public int getCreatedBy(){
        return this.createdBy;
    }

    public DateFormat getCreatedAt(){
        return this.createdAt;
    }

    public DateFormat getUpdatedAt(){
        return this.updatedAt;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setUpdatedAt(DateFormat updatedAt){
        this.updatedAt = updatedAt;
    }
}
