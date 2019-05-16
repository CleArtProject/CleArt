package com.example.pascalisnala.cleart.models;

import com.google.gson.annotations.SerializedName;

public class UserReview {
    @SerializedName("userid")
    private int userid;

    @SerializedName("attrname")
    private String attrname;

    @SerializedName("rating")
    private int rating;

    @SerializedName("review")
    private String review;

    @SerializedName("datecreated")
    private String datecreated;

    @SerializedName("attrid")
    private int attrid;

    public UserReview(int userid, String attrname, int rating, String review, String datecreated, int attrid) {
        this.userid = userid;
        this.attrname = attrname;
        this.rating = rating;
        this.review = review;
        this.datecreated = datecreated;
        this.attrid = attrid;
    }

    public int getUserid() {
        return userid;
    }

    public String getAttrname() {
        return attrname;
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
