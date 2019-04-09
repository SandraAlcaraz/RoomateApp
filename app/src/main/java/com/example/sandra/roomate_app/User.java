package com.example.sandra.roomate_app;

public class User {
    private String id, email, name;
    private int groupId;

    public User(){

    }

    public User(String id, String email, String name, int groupId){
        this.id = id;
        this.email = email;
        this.name = name;
        this.groupId = groupId;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setGroupId(int groupId){
        this.groupId = groupId;
    }

    public String getId(){
        return this.id;
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
