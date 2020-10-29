package com.example.laundryapp.user.response;

import com.google.gson.annotations.SerializedName;

public class ComonResponse {
    @SerializedName("status")
    public String status;
    @SerializedName("message")
    public String message;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }


}
