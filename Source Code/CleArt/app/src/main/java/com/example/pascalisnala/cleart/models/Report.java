package com.example.pascalisnala.cleart.models;

import java.util.ArrayList;

public class Report {
    private User user;
    private int reportid;
    private String specifics;
    private String issue;
    private String comment;
    private String datecreated;
    private String status;
    private Attraction attraction;
    private ArrayList<String> image;

    public User getUser() {
        return user;
    }

    public int getReportid() {
        return reportid;
    }

    public String getSpecifics() {
        return specifics;
    }

    public String getIssue() {
        return issue;
    }

    public String getComment() {
        return comment;
    }

    public String getDatecreated() {
        return datecreated;
    }

    public String getStatus() {
        return status;
    }

    public Attraction getAttraction() {
        return attraction;
    }

    public ArrayList<String> getImage() {
        return image;
    }

    public Report(User user, int reportid, String specifics, String issue, String comment, String datecreated, String status, Attraction attraction, ArrayList<String> image) {
        this.user = user;
        this.reportid = reportid;
        this.specifics = specifics;
        this.issue = issue;
        this.comment = comment;
        this.datecreated = datecreated;
        this.status = status;
        this.attraction = attraction;
        this.image = image;


    }
}
