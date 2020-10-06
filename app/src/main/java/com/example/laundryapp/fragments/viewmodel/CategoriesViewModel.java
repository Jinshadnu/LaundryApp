package com.example.laundryapp.fragments.viewmodel;

import com.example.laundryapp.fragments.pojo.Categories;
import com.example.laundryapp.fragments.repository.CategoryRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class CategoriesViewModel extends ViewModel {
    public CategoryRepository categoryRepository;

    public CategoriesViewModel() {
        this.categoryRepository=new CategoryRepository();
    }

    public LiveData<List<Categories>> getCategories(){
        return categoryRepository.fetchCategories();
    }
}
