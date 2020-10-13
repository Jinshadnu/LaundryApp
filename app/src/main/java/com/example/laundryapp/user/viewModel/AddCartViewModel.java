package com.example.laundryapp.user.viewModel;

import com.example.laundryapp.user.repository.AddCartRepository;
import com.example.laundryapp.user.response.CartResponse;
import com.example.laundryapp.utilities.CommonResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class AddCartViewModel extends ViewModel {
    public AddCartRepository addCartRepository;

    public AddCartViewModel() {
        this.addCartRepository=new AddCartRepository();
    }

    public LiveData<CommonResponse> addToCart(int user_id,String service_name,int item_id,int quantity,int price){
        return addCartRepository.addToCart(user_id, service_name, item_id, quantity, price);
    }

    public LiveData<CartResponse> getCartItems(int user_id){
        return addCartRepository.getCartItems(user_id);
    }


 }
