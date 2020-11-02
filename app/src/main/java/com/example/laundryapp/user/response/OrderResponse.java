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



    public class Order {

        @SerializedName("order_id")
        public String order_id;

        @SerializedName("Date")
        public String date;

        @SerializedName("order_status")
        public String order_status;

        @SerializedName("items")
        public ArrayList<Itemss> items;

        public String getDate() {
            return date;
        }

        public String getOrder_status() {
            return order_status;
        }

        public String getOrder_id() {
            return order_id;
        }

        public ArrayList<Itemss> getItems() {
            return items;
        }



        public class Itemss {
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
