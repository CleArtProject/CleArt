package com.example.pascalisnala.cleart.responses;

import com.example.pascalisnala.cleart.models.AttrReview;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AttrReviewResponse {
    @SerializedName("error")
    private boolean error;

    @SerializedName("review")
    private List<AttrReview> attrreview;

    public AttrReviewResponse(boolean error, List<AttrReview> attrreview) {
        this.error = error;
        this.attrreview = attrreview;
    }

    public boolean isError() {
        return error;
    }

    public List<AttrReview> getAttrreview() {
        return attrreview;
    }
}
