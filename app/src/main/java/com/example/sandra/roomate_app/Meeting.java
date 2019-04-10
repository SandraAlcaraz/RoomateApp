package com.example.sandra.roomate_app;

import java.text.DateFormat;

public class Meeting {
    private String id, groupId, event;
    private int createdBy;
    private DateFormat startDate, endDate, createdAt;

    public Meeting(String id, String groupId, String event, int createdBy, DateFormat startDate, DateFormat endDate, DateFormat createdAt){
        this.id = id;
        this.groupId = groupId;
        this.event = event;
        this.createdBy = createdBy;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = createdAt;
    }

    public String getId(){
        return this.id;
    }

    public String getGroupId(){
        return this.groupId;
    }

    public String getEvent(){
        return this.event;
    }

    public int getCreatedBy(){
        return this.createdBy;
    }

    public DateFormat getStartDate(){
        return this.startDate;
    }

    public DateFormat getEndDate(){
        return this.endDate;
    }

    public DateFormat getCreatedAt(){
        return this.createdAt;
    }

    public void setEvent(String event){
        this.event = event;
    }

    public void setStartDate(DateFormat startDate){
        this.startDate = startDate;
    }

    public void setEndDate(DateFormat endDate){
        this.endDate = endDate;
    }
}
