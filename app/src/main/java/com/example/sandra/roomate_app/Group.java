package com.example.sandra.roomate_app;

import java.util.ArrayList;

public class Group {
    private String code, name, createdBy, adminId;
    private int size;
    private ArrayList<String> members;

    public Group(){

    }

    public Group(String code, String name, int size, String createdBy, String adminId, ArrayList<String> members){
        this.code = code;
        this.name = name;
        this.size = size;
        this.createdBy = createdBy;
        this.adminId = adminId;
        this.members = members;
    }

    public String getCode() {
        return this.code;
    }

    public String getName(){
        return this.name;
    }

    public int getSize(){
        return this.size;
    }

    public String getCreatedBy(){
        return this.createdBy;
    }

    public String getAdminId(){
        return this.adminId;
    }

    public ArrayList<String> getMembers(){
        return this.members;
    }

    public int numberOfMembers(){
        return this.members.size();
    }

    public void setName(String name){
        this.name = name;
    }

    public void setSize(int size){
        this.size = size;
    }

    public void setAdmin(String adminId){
        this.adminId = adminId;
    }
}
