package com.example.laundryapp.fragments.pojo;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

public class Banners {
    public int id;
    public int banner_image;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBanner_image() {
        return banner_image;
    }

    public void setBanner_image(int banner_image) {
        this.banner_image = banner_image;
    }

    public Banners(int id, int banner_image) {
        this.id = id;
        this.banner_image = banner_image;
    }


}
