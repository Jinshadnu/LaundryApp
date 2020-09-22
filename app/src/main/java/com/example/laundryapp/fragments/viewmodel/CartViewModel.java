package com.example.laundryapp.fragments.viewmodel;

import com.example.laundryapp.fragments.pojo.Cart;
import com.example.laundryapp.fragments.repository.CartRepostory;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class CartViewModel extends ViewModel {
 public CartRepostory cartRepostory;
    public CartViewModel() {
        this.cartRepostory=new CartRepostory();
    }

    public LiveData<List<Cart>> getcartItems(){
        return cartRepostory.getcartItems();
    }
}
