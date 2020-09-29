package com.example.laundryapp.utilities;

import com.google.gson.annotations.SerializedName;

public class CommonResponse {

    /* ------------------------------------------------------------- *
     * Private Members
     * ------------------------------------------------------------- */

    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;



    /* ------------------------------------------------------------- *
     * Constructor
     * ------------------------------------------------------------- */

    public CommonResponse(String result, String message) {
        this.status = result;
        this.message = message;
    }

    /* ------------------------------------------------------------- *
     * Getters
     * ------------------------------------------------------------- */

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
