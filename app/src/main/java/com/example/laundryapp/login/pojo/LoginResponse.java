package com.example.laundryapp.login.pojo;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("user")
    private User user;

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public String getStatus() {
        return status;
    }





}
