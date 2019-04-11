package com.example.sandra.roomate_app;

public class User {
    private String email, name;
    private String groupId;

    public User(){

    }

    public User(String email, String name){
        this.email = email;
        this.name = name;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setGroupId(String groupId){
        this.groupId = groupId;
    }

    public String getEmail(){
        return this.email;
    }

    public String getName(){
        return this.name;
    }

    public String getGroupId(){
        return this.groupId;
    }
}
