package com.example.laundryapp.user.response;

import com.example.laundryapp.fragments.pojo.Orders;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OrderResponse {
    @SerializedName("orders")
    private ArrayList<Order> orders;

    @SerializedName("status")
    private String status;

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public String getStatus() {
        return status;
    }



    private class Order {
        @SerializedName("date")
        private String date;

        @SerializedName("order_status")
        private String order_status;

        @SerializedName("service_name")
        private String service_name;

        @SerializedName("order_id")
        private String order_id;

        @SerializedName("items")
        private ArrayList<Itemss> items;

        public String getDate() {
            return date;
        }

        public String getOrder_status() {
            return order_status;
        }

        public String getService_name() {
            return service_name;
        }

        public String getOrder_id() {
            return order_id;
        }

        public ArrayList<Itemss> getItems() {
            return items;
        }



        private class Itemss {
            @SerializedName("quantity")
            private String quantity;

            @SerializedName("item_id")
            private String item_id;

            @SerializedName("item_name")
            private String item_name;

            public String getQuantity() {
                return quantity;
            }

            public String getItem_id() {
                return item_id;
            }

            public String getItem_name() {
                return item_name;
            }




        }
    }
}
