package com.example.sandra.roomate_app;

public class User {
    private String email, name;
    private int groupId;

    public User(){

    }

    public User(String email, String name){
        this.email = email;
        this.name = name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setGroupId(int groupId){
        this.groupId = groupId;
    }

    public String getEmail(){
        return this.email;
    }

    public String getName(){
        return this.name;
    }

    public int getGroupId(){
        return this.groupId;
    }
}
