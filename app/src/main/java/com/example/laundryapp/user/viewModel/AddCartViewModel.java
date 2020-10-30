package com.example.laundryapp.user.viewModel;

import com.example.laundryapp.user.repository.AddCartRepository;
import com.example.laundryapp.user.response.CartResponse;
import com.example.laundryapp.user.response.ComonResponse;
import com.example.laundryapp.utilities.CommonResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class AddCartViewModel extends ViewModel {
    public AddCartRepository addCartRepository;

    public AddCartViewModel() {
        this.addCartRepository=new AddCartRepository();
    }

    public LiveData<ComonResponse> addToCart(String user_id, String service_name, String item_id, String quantity, String price){
        return addCartRepository.addToCart(user_id, service_name, item_id, quantity, price);
    }

    public LiveData<CartResponse> getCartItems(String user_id){
        return addCartRepository.getCartItems(user_id);
    }

    public LiveData<ComonResponse> deletecartItem(String user_id,String item_id){
        return addCartRepository.deletecartItem(user_id,item_id);
    }

 }
