package com.example.laundryapp.user.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OrderedItemResponse {

    @SerializedName("status")
    public String status;

    public String getStatus() {
        return status;
    }

    public ArrayList<OrderItems> getItems() {
        return items;
    }

    @SerializedName("items")
    public ArrayList<OrderItems> items;


    public class OrderItems {
        @SerializedName("quantity")
        public String quantiy;

        @SerializedName("service_name")
        public String service_name;

        @SerializedName("item_name")
        public String item_name;

        public String getQuantiy() {
            return quantiy;
        }

        public String getService_name() {
            return service_name;
        }

        public String getItem_name() {
            return item_name;
        }






    }
}
