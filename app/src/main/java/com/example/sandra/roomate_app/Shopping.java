package com.example.sandra.roomate_app;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.util.Date;

public class Shopping implements Parcelable {
    private String id, description, groupId, assigneeName, createdBy;
    private boolean finished;
    private Date createdAt, dueDate, finishedAt, updatedAt;

    public Shopping(String id, String description, String groupId, String createdBy, Date createdAt, Date dueDate, String assigneeName){
        this.id = id;
        this.description = description;
        this.groupId = groupId;

        this.createdBy = createdBy;
        this.finished = false;
        this.createdAt = createdAt;
        this.dueDate = dueDate;
        this.assigneeName=assigneeName;
    }


    public Shopping(){

    }

    protected Shopping(Parcel in) {
        id = in.readString();
        description = in.readString();
        groupId = in.readString();
        assigneeName = in.readString();
        createdBy = in.readString();
        finished = in.readByte() != 0;
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

    public String getDescription(){
        return this.description;
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

    public void setDescription(String description){
        this.description = description;
    }

    public void setCreatedBy(String createdBy){
        this.createdBy = createdBy;
    }


    public void setFinished(boolean finished){
        this.finished = finished;
    }

    public Boolean getFinished(){
        return this.finished;
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
        dest.writeString(description);
        dest.writeString(groupId);
        dest.writeString(assigneeName);
        dest.writeString(createdBy);
        dest.writeByte((byte) (finished ? 1 : 0));
    }
}
