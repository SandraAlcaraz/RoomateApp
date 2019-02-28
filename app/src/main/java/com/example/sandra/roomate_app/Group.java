package com.example.sandra.roomate_app;

import java.util.ArrayList;

public class Group {
    private String code, name;
    private ArrayList<User> users;

    public Group(String code, String name, ArrayList<User> users){
        this.code = code;
        this.name = name;
        this.users = users;
    }

    public String getCode() {
        return this.code;
    }

    public String getName(){
        return this.name;
    }

    public ArrayList<User> getUsers(){
        return this.users;
    }

    public int numberOfUsers(){
        return this.users.size();
    }
}
