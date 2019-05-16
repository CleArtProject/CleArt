package com.coba.cleartemployee.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AttrFixedResponse {
    @SerializedName("error")
    private String error;

    @SerializedName("report")
    private List<AttrFixed> attrFixed;

    public AttrFixedResponse(String error, List<AttrFixed> attrFixed) {
        this.error = error;
        this.attrFixed = attrFixed;
    }

    public String getError() {
        return error;
    }

    public List<AttrFixed> getAttrReports() {
        return attrFixed;
    }
}
