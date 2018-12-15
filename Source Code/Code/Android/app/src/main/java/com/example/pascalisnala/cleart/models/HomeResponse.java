package com.example.pascalisnala.cleart.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeResponse {
    @SerializedName("error")
    private boolean error;

    @SerializedName("attraction")
    private List<Attraction> attraction;

    public HomeResponse(boolean error, List<Attraction> attraction) {
        this.error = error;
        this.attraction = attraction;
    }

    public boolean isError() {
        return error;
    }

    public List<Attraction> getAttraction() {
        return attraction;
    }
}
