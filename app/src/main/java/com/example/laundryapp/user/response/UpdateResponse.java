package com.example.laundryapp.user.response;

import com.google.gson.annotations.SerializedName;

public class UpdateResponse {

    @SerializedName("status")
    public String status;

    public String getStatus() {
        return status;
    }



}
