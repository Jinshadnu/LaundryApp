package com.example.laundryapp.user.response;

import com.google.gson.annotations.SerializedName;

public class UpdateResponse {

    @SerializedName("status")
    public String status;


    @SerializedName("order_total")
    private int order_total;



    public String getStatus() {
        return status;
    }

    public int getOrder_total() {
        return order_total;
    }



}
