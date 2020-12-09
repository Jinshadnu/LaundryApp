package com.example.laundryapp.user.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserResponse {
    @SerializedName("status")
    public String status;

    public String getStatus() {
        return status;
    }

    public ArrayList<Users> getUser() {
        return user;
    }

    @SerializedName("user")
    public ArrayList<Users> user;

    public class Users {
        @SerializedName("user_id")
        public String user_id;

        @SerializedName("phone")
        public String phone;

        @SerializedName("email")
        public String email;

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

        @SerializedName("username")
        public String username;


    }
}
