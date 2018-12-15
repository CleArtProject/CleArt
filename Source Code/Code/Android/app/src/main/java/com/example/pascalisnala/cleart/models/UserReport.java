package com.example.pascalisnala.cleart.models;

import com.google.gson.annotations.SerializedName;

public class UserReport {

    @SerializedName("userid")
    private int userid;

    @SerializedName("attrname")
    private String attrname;

    @SerializedName("reportid")
    private int reportid;

    @SerializedName("specifics")
    private String specifics;

    @SerializedName("issue")
    private String issue;

    @SerializedName("comment")
    private String comment;

    @SerializedName("datecreated")
    private String datecreated;

    @SerializedName("status")
    private String status;

    @SerializedName("attrid")
    private int attrid;

    public UserReport(int userid, String attrname, int reportid, String specifics, String issue, String comment, String datecreated, String status, int attrid) {
        this.userid = userid;
        this.attrname = attrname;
        this.reportid = reportid;
        this.specifics = specifics;
        this.issue = issue;
        this.comment = comment;
        this.datecreated = datecreated;
        this.status = status;
        this.attrid = attrid;
    }

    public int getUserid() {
        return userid;
    }

    public String getAttrname() {
        return attrname;
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

    public int getAttrid() {
        return attrid;
    }
}
