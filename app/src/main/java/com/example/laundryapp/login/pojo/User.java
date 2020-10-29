package com.example.laundryapp.login.pojo;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("username")
    private String username;

    @SerializedName("phone")
    private String phone;

    @SerializedName("email")
    private String email;

    public String getUser_id() {
        return user_id;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }










}
