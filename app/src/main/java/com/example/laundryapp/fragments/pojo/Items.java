package com.example.laundryapp.fragments.pojo;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

public class Items {
    public int product_id;
    public String product_name;
    public int product_price;
    public int product_image;



    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int quantity;

    public Items(String product_name, int product_price, int product_image) {
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_image = product_image;
    }


    public int getProduct_image() {
        return product_image;
    }

    public void setProduct_image(int product_image) {
        this.product_image = product_image;
    }







    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }
    @BindingAdapter("items")
    public static void loadImage(ImageView imageView, int image){
        imageView.setImageResource(image);
    }

    public int getQuantity() {
        return quantity;
    }


}
