package com.example.sandra.roomate_app;

import java.text.DateFormat;

public class ShoppingItem {
    private String id, groupId, description;
    private int createdBy, finishedBy;
    private boolean finished;
    private DateFormat finishedAt, createdAt;

    public ShoppingItem(String id, String groupId, String description, int createdBy, int finishedBy, DateFormat createdAt){
        this.id = id;
        this.groupId = groupId;
        this.description = description;
        this.createdBy = createdBy;
        this.finished = false;
        this.finishedBy = finishedBy;
        this.createdAt = createdAt;
    }

    public String getId(){
        return this.id;
    }

    public String getGroupId(){
        return this.groupId;
    }

    public String getDescription(){
        return this.description;
    }

    public int getCreatedBy(){
        return this.createdBy;
    }

    public int getFinishedBy(){
        return this.finishedBy;
    }

    public boolean getFinished(){
        return this.finished;
    }

    public DateFormat getCreatedAt(){
        return this.createdAt;
    }

    public DateFormat getFinishedAt(){
        return this.finishedAt;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setFinishedBy(int finishedBy){
        this.finishedBy = finishedBy;
    }

    public void setFinishedAt(DateFormat finishedAt){
        this.finishedAt = finishedAt;
    }

}
