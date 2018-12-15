package com.example.pascalisnala.cleart.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Attraction {

    @SerializedName("attrid")
    private int attrid;

    @SerializedName("attrname")
    private String attrname;

    @SerializedName("location")
    private String location;

    @SerializedName("details")
    private String details;

    @SerializedName("schedule")
    private String schedule;

    @SerializedName("category")
    private String category;
    
    @SerializedName("rating")
    private Float rating;

    @SerializedName("facilty")
    private String[] facility;


    public Attraction(int attrid, String attrname, String location, String details, String schedule, String category, Float rating, String[] facility) {
        this.attrid = attrid;
        this.attrname = attrname;
        this.location = location;
        this.details = details;
        this.schedule = schedule;
        this.category = category;
        this.rating = rating;
        this.facility = facility;
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

    public String getDetails() {
        return details;
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

    public String[] getFacility() {
        return facility;
    }
}
