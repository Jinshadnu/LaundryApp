package com.example.laundryapp.user.repository;

import com.example.laundryapp.core.NetworkAPI;
import com.example.laundryapp.core.NetworkService;
import com.example.laundryapp.user.pojo.OrderedItemResponse;
import com.example.laundryapp.user.response.ComonResponse;
import com.example.laundryapp.user.response.OrderResponse;
import com.example.laundryapp.utilities.CommonResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class OrderRepository {
    public NetworkAPI networkAPI;
    public OrderRepository() {
    }

    public LiveData<ComonResponse> addOrder(String user_id, String building_number, String street_number, String zone,String latitude,String longitude,String address,int orderType){
        MutableLiveData mutableLiveData=new MutableLiveData();

        networkAPI= NetworkService.getRetrofitInstance().create(NetworkAPI.class);

        Call<ComonResponse> responseCall=networkAPI.addOrder(user_id,building_number,street_number,zone,latitude,longitude,address,orderType);
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

    public LiveData<OrderResponse> getOrders(String user_id){
        MutableLiveData mutableLiveData=new MutableLiveData();

        networkAPI= NetworkService.getRetrofitInstance().create(NetworkAPI.class);
        Call<OrderResponse> responseCall=networkAPI.getOrders(user_id);

        responseCall.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                OrderResponse orderResponse=response.body();
                if (orderResponse != null){
                    mutableLiveData.setValue(orderResponse);
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                mutableLiveData.setValue(null);
            }
        });
        return mutableLiveData;
    }

    public LiveData<ComonResponse> cancelOrder(String orderId){
        MutableLiveData mutableLiveData=new MutableLiveData();

        networkAPI=NetworkService.getRetrofitInstance().create(NetworkAPI.class);
        Call<ComonResponse> responseCall=networkAPI.orderCancel(orderId);
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

    public LiveData<OrderedItemResponse> getItems(String orderId){
        MutableLiveData mutableLiveData=new MutableLiveData();

        networkAPI=NetworkService.getRetrofitInstance().create(NetworkAPI.class);
        Call<OrderedItemResponse> responseCall=networkAPI.getOrderedItems(orderId);
        responseCall.enqueue(new Callback<OrderedItemResponse>() {
            @Override
            public void onResponse(Call<OrderedItemResponse> call, Response<OrderedItemResponse> response) {
                OrderedItemResponse itemResponse=response.body();
                if (itemResponse != null){
                    mutableLiveData.setValue(itemResponse);
                }
            }

            @Override
            public void onFailure(Call<OrderedItemResponse> call, Throwable t) {
             mutableLiveData.setValue(null);
            }
        });
        return mutableLiveData;
    }

}
