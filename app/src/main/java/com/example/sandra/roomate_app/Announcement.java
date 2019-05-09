package com.example.sandra.roomate_app;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.util.Date;

public class Announcement implements Parcelable {
    private String id, content, groupId, assigneeName, createdBy;
    private Date createdAt, dueDate, finishedAt, updatedAt;

    public Announcement(String id, String content, String groupId, String createdBy, Date createdAt, Date dueDate, String assigneeName){
        this.id = id;
        this.content = content;
        this.groupId = groupId;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.dueDate = dueDate;
        this.assigneeName=assigneeName;
    }


    public Announcement(){

    }

    protected Announcement(Parcel in) {
        id = in.readString();
        content = in.readString();
        groupId = in.readString();
        createdBy = in.readString();
    }

    public static final Creator<Shopping> CREATOR = new Creator<Shopping>() {
        @Override
        public Shopping createFromParcel(Parcel in) {
            return new Shopping(in);
        }

        @Override
        public Shopping[] newArray(int size) {
            return new Shopping[size];
        }
    };

    public String getId(){
        return this.id;
    }

    public String getContent(){
        return this.content;
    }

    public String getGroupId(){
        return this.groupId;
    }



    public String getCreatedBy(){
        return this.createdBy;
    }

    public Date getCreatedAt(){
        return this.createdAt;
    }

    public Date getDueDate(){
        return this.dueDate;
    }

    public Date getFinishedAt(){
        return this.finishedAt;
    }

    public Date getUpdatedAt(){
        return this.updatedAt;
    }

    public void setContent(String description){
        this.content = description;
    }

    public void setCreatedBy(String createdBy){
        this.createdBy = createdBy;
    }


    public void setDueDate(Date dueDate){
        this.dueDate = dueDate;
    }

    public void setUpdatedAt(Date updatedAt){
        this.updatedAt = updatedAt;
    }

    public void setFinishedAt(Date finishedAt){
        this.finishedAt = finishedAt;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(content);
        dest.writeString(groupId);
        dest.writeString(createdBy);
    }
}