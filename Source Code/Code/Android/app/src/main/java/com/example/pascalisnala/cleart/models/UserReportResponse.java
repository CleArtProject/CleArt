package com.example.pascalisnala.cleart.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserReportResponse {
    @SerializedName("error")
    private boolean error;

    @SerializedName("report")
    private List<UserReport> userReports;

    public UserReportResponse(boolean error, List<UserReport> userReports) {
        this.error = error;
        this.userReports = userReports;
    }

    public boolean isError() {
        return error;
    }

    public List<UserReport> getUserReports() {
        return userReports;
    }
}
