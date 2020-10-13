package com.example.laundryapp.user.response;

import com.example.laundryapp.fragments.pojo.Cart;

import java.util.ArrayList;

public class CartResponse {
    private ArrayList<Carts> cart;

    private String status;

    public ArrayList<Carts> getCart() {
        return cart;
    }




    public String getStatus ()
    {
        return status;
    }


    @Override
    public String toString()
    {
        return "ClassPojo [cart = "+cart+", status = "+status+"]";
    }

    private class Carts {
        private String quantity;

        private String item_id;

        private String service_name;

        private String price;

        private String item_image;

        private String item_name;


        public String getQuantity() {
            return quantity;
        }

        public String getItem_id() {
            return item_id;
        }

        public String getService_name() {
            return service_name;
        }

        public String getPrice() {
            return price;
        }

        public String getItem_image() {
            return item_image;
        }

        public String getItem_name() {
            return item_name;
        }



    }
}
