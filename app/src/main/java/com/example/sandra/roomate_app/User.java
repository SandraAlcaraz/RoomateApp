package com.example.sandra.roomate_app;

public class User {
    private String id, email, name, groupId;

    public User(String id, String email){
        this.id = id;
        this.email = email;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setGroupId(String groupId){
        this.groupId = groupId;
    }
}
