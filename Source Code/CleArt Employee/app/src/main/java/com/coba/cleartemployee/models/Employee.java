package com.coba.cleartemployee.models;


import com.google.gson.annotations.SerializedName;

public class Employee {
    private int attrid;
    private int empid;
    private String name;
    @SerializedName("phone number")
    private String phonenum;
    private String username;

    public Employee(int attrid, int empid, String name, String phonenum, String username) {
        this.attrid = attrid;
        this.empid = empid;
        this.name = name;
        this.phonenum = phonenum;
        this.username = username;
    }

    public int getAttrid(){
        return attrid;
    }
    public int getEmpid() {
        return empid;
    }
    public String getName() {
        return name;
    }
    public String getPhonenum() {
        return phonenum;
    }
    public String getUsername() {
        return username;
    }
}