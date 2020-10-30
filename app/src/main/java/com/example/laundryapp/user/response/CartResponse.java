package com.example.laundryapp.user.response;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.laundryapp.fragments.pojo.Cart;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CartResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("cart")
    public ArrayList<Carts> cart;

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

    public class Carts {
        @SerializedName("quantity")
        private String quantity;

        @SerializedName("item_id")
        private String item_id;

        @SerializedName("service_name")
        private String service_name;

        @SerializedName("price")
        private String price;

        @SerializedName("item_image")
        private String item_image;

        @SerializedName("item_name")
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

    @BindingAdapter({"cartitem_image"})
    public static void loadItemImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl).apply(new RequestOptions().circleCrop())
                .into(view);
    }
}
