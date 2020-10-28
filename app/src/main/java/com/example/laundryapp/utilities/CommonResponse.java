package com.example.laundryapp.utilities;

import com.google.gson.annotations.SerializedName;

public class CommonResponse {

    /* ------------------------------------------------------------- *
     * Private Members
     * ------------------------------------------------------------- */


    @SerializedName("status")
    private String status;






    @SerializedName("error_message")
    private String error_message;



    /* ------------------------------------------------------------- *
     * Constructor
     * ------------------------------------------------------------- */

    public CommonResponse(String status, String error_message) {
        this.status = status;
        this.error_message = error_message;
    }

    /* ------------------------------------------------------------- *
     * Getters
     * ------------------------------------------------------------- */

    public String getStatus() {
        return status;
    }

    public String getError_message() {
        return error_message;
    }


}