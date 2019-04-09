package com.example.sandra.roomate_app;

import java.util.ArrayList;

public class Group {
    private String id, code, name;
    private int size, createdBy, adminId;
    private ArrayList<User> members;

    public Group(String id, String code, String name, int size, int createdBy, int adminId, ArrayList<User> members){
        this.id = id;
        this.code = code;
        this.name = name;
        this.size = size;
        this.createdBy = createdBy;
        this.adminId = adminId;
        this.members = members;
    }

    public String getId(){
        return this.id;
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

    public int getCreatedBy(){
        return this.createdBy;
    }

    public int getAdminId(){
        return this.adminId;
    }

    public ArrayList<User> getUsers(){
        return this.members;
    }

    public int numberOfUsers(){
        return this.members.size();
    }

    public void setName(String name){
        this.name = name;
    }

    public void setSize(int size){
        this.size = size;
    }

    public void setAdmin(int adminId){
        this.adminId = adminId;
    }
}
