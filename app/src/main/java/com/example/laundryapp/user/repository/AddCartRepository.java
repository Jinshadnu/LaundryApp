package com.example.laundryapp.user.repository;

import com.example.laundryapp.core.NetworkAPI;
import com.example.laundryapp.core.NetworkService;
import com.example.laundryapp.user.response.CartResponse;
import com.example.laundryapp.utilities.CommonResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCartRepository {
    public NetworkAPI networkAPI;

    public AddCartRepository() {
    }

    public LiveData<CommonResponse> addToCart(int user_id,String service_name,int item_id,int quantity,int price){
        MutableLiveData mutableLiveData=new MutableLiveData();

        networkAPI= NetworkService.getRetrofitInstance().create(NetworkAPI.class);
        Call<CommonResponse> responseCall=networkAPI.addToCart(user_id,service_name,item_id,quantity,price);

        responseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                CommonResponse commonResponse=response.body();
                if (commonResponse != null){
                    mutableLiveData.postValue(commonResponse);
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
             mutableLiveData.postValue(null);
            }
        });
        return mutableLiveData;
    }

    public LiveData<CartResponse> getCartItems(int cart_id){
        MutableLiveData mutableLiveData=new MutableLiveData();

        networkAPI=NetworkService.getRetrofitInstance().create(NetworkAPI.class);
        Call<CartResponse> cartResponseCall=networkAPI.getCartItems(cart_id);
        cartResponseCall.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                 CartResponse cartResponse=response.body();
                 if (cartResponse != null){
                     mutableLiveData.setValue(cartResponse);
                 }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
             mutableLiveData.setValue(null);
            }
        });
        return mutableLiveData;
    }
}
