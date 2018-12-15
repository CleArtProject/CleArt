package com.example.pascalisnala.cleart.models;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("userid")
    private int userid;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("phone number")
    private String phonenum;

    @SerializedName("username")
    private String username;

    public User(int userid, String name, String email, String phonenum, String username) {
        this.userid = userid;
        this.name = name;
        this.email = email;
        this.phonenum = phonenum;
        this.username = username;
    }

    public int getUserid() {
        return userid;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPhonenum() {
        return phonenum;
    }
    public String getUsername() {
        return username;
    }
}
