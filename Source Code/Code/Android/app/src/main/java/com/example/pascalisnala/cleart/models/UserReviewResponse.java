package com.example.pascalisnala.cleart.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserReviewResponse {
    @SerializedName("error")
    private boolean error;

    @SerializedName("review")
    private List<UserReview> userReviews;

    public UserReviewResponse(boolean error, List<UserReview> userReviews) {
        this.error = error;
        this.userReviews = userReviews;
    }

    public boolean isError() {
        return error;
    }

    public List<UserReview> getUserReviews() {
        return userReviews;
    }
}
