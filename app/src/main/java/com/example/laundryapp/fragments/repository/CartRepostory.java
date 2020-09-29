package com.example.laundryapp.fragments.repository;

import com.example.laundryapp.R;
import com.example.laundryapp.fragments.pojo.Cart;
import com.example.laundryapp.fragments.pojo.Items;
import com.example.laundryapp.utilities.CommonResponse;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class CartRepostory {

    public CartRepostory() {
    }

    public LiveData<List<Cart>> getcartItems(){
        MutableLiveData mutableLiveData=new MutableLiveData();

        List<Cart> cartList=new ArrayList<>();
        cartList.add(new Cart("T-shirt",R.drawable.t_shirt,5,1));
        cartList.add(new Cart("Shirt", R.drawable.shirt,7,1));
        cartList.add(new Cart("Pant", R.drawable.pant,10,1));
        cartList.add(new Cart("Shorts",R.drawable.shorts,4,1));
        cartList.add(new Cart("Shirt", R.drawable.shirt,7,1));
        cartList.add(new Cart("T-shirt",R.drawable.t_shirt,5,1));
        cartList.add(new Cart("Shorts",R.drawable.shorts,4,1));

        mutableLiveData.setValue(cartList);

        return mutableLiveData;
    }

    public LiveData<CommonResponse> addToCart(){
        MutableLiveData mutableLiveData=new MutableLiveData();
        return mutableLiveData;
    }
}
