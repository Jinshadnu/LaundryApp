package com.example.laundryapp.login.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LoginResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("user")
    private ArrayList<User> user;


    public ArrayList<User> getUser() {
        return user;
    }


    public String getMessage() {
        return message;
    }



    public String getStatus() {
        return status;
    }





}
