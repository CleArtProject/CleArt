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

    private String image;

    public User(int userid, String name, String email, String phonenum, String username, String image) {
        this.userid = userid;
        this.name = name;
        this.email = email;
        this.phonenum = phonenum;
        this.username = username;
        this.image = image;
    }

    public User(int userid, String name, String email, String phonenum, String username) {
        this.userid = userid;
        this.name = name;
        this.email = email;
        this.phonenum = phonenum;
        this.username = username;
        this.image = null;
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

    public String getImage() {
        return image;
    }
}
