package com.example.laundryapp.register.pojo;

import com.example.laundryapp.login.pojo.User;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RegisterResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("erorr_message")
    private String erorr_message;

    @SerializedName("user")
    private ArrayList<User> user;

    public String getStatus() {
        return status;
    }

    public String getErorr_message() {
        return erorr_message;
    }

    public ArrayList<User> getUser() {
        return user;
    }




}
