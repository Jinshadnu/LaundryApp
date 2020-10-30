package com.example.laundryapp.user.repository;

import com.example.laundryapp.core.NetworkAPI;
import com.example.laundryapp.core.NetworkService;
import com.example.laundryapp.user.response.CartResponse;
import com.example.laundryapp.user.response.ComonResponse;
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

    public LiveData<ComonResponse> addToCart(String user_id, String service_name, String item_id, String quantity, String price){
        MutableLiveData mutableLiveData=new MutableLiveData();

        networkAPI= NetworkService.getRetrofitInstance().create(NetworkAPI.class);
        Call<ComonResponse> responseCall=networkAPI.addToCart(user_id,service_name,item_id,quantity,price);

        responseCall.enqueue(new Callback<ComonResponse>() {
            @Override
            public void onResponse(Call<ComonResponse> call, Response<ComonResponse> response) {
                ComonResponse commonResponse=response.body();
                if (commonResponse != null){
                    mutableLiveData.postValue(commonResponse);
                }
            }

            @Override
            public void onFailure(Call<ComonResponse> call, Throwable t) {
             mutableLiveData.postValue(null);
            }
        });
        return mutableLiveData;
    }

    public LiveData<CartResponse> getCartItems(String cart_id){
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

    public LiveData<ComonResponse> deletecartItem(String user_id,String item_id){
        MutableLiveData mutableLiveData=new MutableLiveData();

        networkAPI=NetworkService.getRetrofitInstance().create(NetworkAPI.class);
        Call<ComonResponse> responseCall=networkAPI.deleteCartItem(user_id,item_id);
        responseCall.enqueue(new Callback<ComonResponse>() {
            @Override
            public void onResponse(Call<ComonResponse> call, Response<ComonResponse> response) {
                ComonResponse comonResponse=response.body();
                if (comonResponse != null){
                    mutableLiveData.postValue(comonResponse);
                }
            }

            @Override
            public void onFailure(Call<ComonResponse> call, Throwable t) {
             mutableLiveData.postValue(null);
            }
        });
        return mutableLiveData;
    }
}
