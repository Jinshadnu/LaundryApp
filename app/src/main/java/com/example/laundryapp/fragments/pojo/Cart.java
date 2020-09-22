package com.example.laundryapp.fragments.pojo;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

public class Cart {
    public int item_id;
    public String item_name;
    public int item_image;

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getItem_image() {
        return item_image;
    }

    public void setItem_image(int item_image) {
        this.item_image = item_image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int price;

    public Cart(String item_name, int item_image, int price) {
        this.item_name = item_name;
        this.item_image = item_image;
        this.price = price;
    }

    @BindingAdapter("carts")
    public static void loadImage(ImageView imageView, int image){
        imageView.setImageResource(image);
    }


}
