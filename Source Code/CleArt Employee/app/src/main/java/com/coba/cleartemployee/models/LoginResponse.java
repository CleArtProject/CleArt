package com.coba.cleartemployee.models;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    private boolean error;
    private String message;
    private Employee employee;


    public LoginResponse(boolean error, String message, Employee employee) {
        this.error = error;
        this.message = message;
        this.employee= employee;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public Employee getEmployee() {
        return employee;
    }
}
