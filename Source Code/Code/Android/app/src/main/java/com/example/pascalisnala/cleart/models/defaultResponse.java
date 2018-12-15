package com.example.pascalisnala.cleart.models;

import com.google.gson.annotations.SerializedName;

public class defaultResponse {

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    public defaultResponse(boolean error, String message) {
        this.error = error;
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
