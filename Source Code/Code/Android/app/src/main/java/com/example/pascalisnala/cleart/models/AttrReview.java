package com.example.pascalisnala.cleart.models;

import com.google.gson.annotations.SerializedName;

public class AttrReview {
    @SerializedName("userid")
    private int userid;

    @SerializedName("username")
    private String username;

    @SerializedName("rating")
    private int rating;

    @SerializedName("review")
    private String review;

    @SerializedName("datecreated")
    private String datecreated;

    @SerializedName("attrid")
    private int attrid;

    public AttrReview(int userid, String username, int rating, String review, String datecreated, int attrid) {
        this.userid = userid;
        this.username = username;
        this.rating = rating;
        this.review = review;
        this.datecreated = datecreated;
        this.attrid = attrid;
    }

    public int getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public int getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }

    public String getDatecreated() {
        return datecreated;
    }

    public int getAttrid() {
        return attrid;
    }
}
