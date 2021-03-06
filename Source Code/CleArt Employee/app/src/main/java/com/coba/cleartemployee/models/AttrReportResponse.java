package com.coba.cleartemployee.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AttrReportResponse {
    @SerializedName("error")
    private String error;

    @SerializedName("report")
    private List<AttrReport> attrReports;

    public AttrReportResponse(String error, List<AttrReport> attrReports) {
        this.error = error;
        this.attrReports = attrReports;
    }

    public String getError() {
        return error;
    }

    public List<AttrReport> getAttrReports() {
        return attrReports;
    }
}
