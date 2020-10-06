package com.example.laundryapp.fragments.repository;

import com.example.laundryapp.fragments.pojo.Cart;
import com.example.laundryapp.fragments.pojo.Categories;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class CategoryRepository {

    public CategoryRepository() {
    }

    public LiveData<List<Categories>> fetchCategories(){
        MutableLiveData mutableLiveData=new MutableLiveData();

        List<Categories> categoriesList=new ArrayList<>();
        categoriesList.add(new Categories(100,"Tops"));
        categoriesList.add(new Categories(100,"Bottom"));
        categoriesList.add(new Categories(100,"Outdoors"));
        categoriesList.add(new Categories(100,"Dresses"));
        categoriesList.add(new Categories(100,"Tops"));
        categoriesList.add(new Categories(100,"Tops"));

        mutableLiveData.setValue(categoriesList);

        return mutableLiveData;
    }
}
