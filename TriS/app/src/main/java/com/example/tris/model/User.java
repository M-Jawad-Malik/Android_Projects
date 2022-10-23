package com.example.tris.model;

public class User {
    private String username;
    private String email;
    public String userId;
    public boolean isAdmin;

    User(){

    }
    User(String username,String email,String userId,boolean isAdmin)
    {
        this.username=username;
        this.email=email;
        this.userId=userId;
        this.isAdmin=isAdmin;
    }
    public void setUsername(String username)
    {
        this.username=username;
    }

    public String getUsername() {
        return username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
