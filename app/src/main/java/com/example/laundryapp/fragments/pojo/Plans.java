package com.example.laundryapp.fragments.pojo;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

public class Plans {
    public int id;
    public int plan_image;

    public Plans(int id, int plan_image) {
        this.id = id;
        this.plan_image = plan_image;
    }



    public int getPlan_image() {
        return plan_image;
    }

    public void setPlan_image(int plan_image) {
        this.plan_image = plan_image;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @BindingAdapter("banners")
    public static void loadImage(ImageView imageView, int image){
        imageView.setImageResource(image);
    }
}
