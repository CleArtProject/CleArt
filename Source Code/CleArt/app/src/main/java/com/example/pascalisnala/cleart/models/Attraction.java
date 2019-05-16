package com.example.pascalisnala.cleart.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Attraction {

    @SerializedName("attrid")
    private int attrid;

    @SerializedName("attrname")
    private String attrname;

    @SerializedName("location")
    private String location;

    @SerializedName("phonenumber")
    private String phonenumber;

    @SerializedName("schedule")
    private String schedule;

    @SerializedName("category")
    private String category;
    
    @SerializedName("rating")
    private Float rating;

    @SerializedName("facilty")
    private ArrayList<String> facility;

    @SerializedName("image")
    private ArrayList<String> image;

    public Attraction(int attrid, String attrname, String location, String phonenumber, String schedule, String category, Float rating, ArrayList<String> facility, ArrayList<String> image) {
        this.attrid = attrid;
        this.attrname = attrname;
        this.location = location;
        this.phonenumber = phonenumber;
        this.schedule = schedule;
        this.category = category;
        this.rating = rating;
        this.facility = facility;
        this.image = image;
    }

    public int getAttrid() {
        return attrid;
    }

    public String getAttrname() {
        return attrname;
    }

    public String getLocation() {
        return location;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getSchedule() {
        return schedule;
    }

    public String getCategory() {
        return category;
    }

    public Float getRating() {
        return rating;
    }

    public ArrayList<String> getFacility() {
        return facility;
    }

    public ArrayList<String> getImage() {
        return image;
    }
}
