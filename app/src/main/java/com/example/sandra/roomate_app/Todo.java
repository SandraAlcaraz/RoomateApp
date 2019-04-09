package com.example.sandra.roomate_app;

import java.text.DateFormat;

public class Todo {
    private String id, description, groupId;
    private int assignee, createdBy;
    private boolean finished;
    private DateFormat createdAt, dueDate, finishedAt, updatedAt;

    public Todo(String id, String description, String groupId, int assignee, int createdBy, DateFormat createdAt, DateFormat dueDate){
        this.id = id;
        this.description = description;
        this.groupId = groupId;
        this.assignee = assignee;
        this.createdBy = createdBy;
        this.finished = false;
        this.createdAt = createdAt;
        this.dueDate = dueDate;
    }

    public String getId(){
        return this.id;
    }

    public String getDescription(){
        return this.description;
    }

    public String getGroupId(){
        return this.groupId;
    }

    public int getAssignee(){
        return this.assignee;
    }

    public int getCreatedBy(){
        return this.createdBy;
    }

    public DateFormat getCreatedAt(){
        return this.createdAt;
    }

    public DateFormat getDueDate(){
        return this.dueDate;
    }

    public DateFormat getFinishedAt(){
        return this.finishedAt;
    }

    public DateFormat getUpdatedAt(){
        return this.updatedAt;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setAssignee(int assignee){
        this.assignee = assignee;
    }

    public void setFinished(boolean finished){
        this.finished = finished;
    }

    public void setDueDate(DateFormat dueDate){
        this.dueDate = dueDate;
    }

    public void setUpdatedAt(DateFormat updatedAt){
        this.updatedAt = updatedAt;
    }

    public void setFinishedAt(DateFormat finishedAt){
        this.finishedAt = finishedAt;
    }
}
