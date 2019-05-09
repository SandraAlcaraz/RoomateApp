package com.example.sandra.roomate_app;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.util.Date;

public class Meeting implements Parcelable {
    private String id, title, groupId, createdBy, date, time;
    private Date createdAt, dueDate, finishedAt, updatedAt;

    public Meeting(String id, String title, String groupId, String createdBy, Date createdAt, Date dueDate, String date, String time){
        this.id = id;
        this.title = title;
        this.groupId = groupId;
        this.date = date;
        this.time = time;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.dueDate = dueDate;
    }


    public Meeting(){

    }

    protected Meeting(Parcel in) {
        id = in.readString();
        title = in.readString();
        date = in.readString();
        time = in.readString();
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

    public String getTitle(){
        return this.title;
    }

    public String getDate(){
        return this.date;
    }

    public String getTime(){
        return this.time;
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

    public void setTitle(String title){
        this.title = title;
    }

    public void setDate(String date){
        this.date = date;
    }

    public void setTime(String time){
        this.time = time;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(groupId);
        dest.writeString(createdBy);
    }
}