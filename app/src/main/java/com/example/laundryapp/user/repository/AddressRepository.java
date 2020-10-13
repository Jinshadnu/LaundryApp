package com.example.laundryapp.user.repository;

import com.example.laundryapp.core.NetworkAPI;
import com.example.laundryapp.core.NetworkService;
import com.example.laundryapp.user.response.AddressResponse;
import com.example.laundryapp.utilities.CommonResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressRepository {
    public NetworkAPI networkAPI;

    public AddressRepository() {
    }

    public LiveData<CommonResponse> addAddress(int id,String building_number,String address,int zone){
        MutableLiveData mutableLiveData=new MutableLiveData();

        networkAPI= NetworkService.getRetrofitInstance().create(NetworkAPI.class);
        Call<CommonResponse> responseCall=networkAPI.addAddress(id,building_number,address,zone);
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

    public LiveData<AddressResponse> getAddress(int user_id){
        MutableLiveData mutableLiveData=new MutableLiveData();

        networkAPI=NetworkService.getRetrofitInstance().create(NetworkAPI.class);
        Call<AddressResponse> responseCall=networkAPI.getAddress(user_id);
        responseCall.enqueue(new Callback<AddressResponse>() {
            @Override
            public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                AddressResponse addressResponse=response.body();
                if (addressResponse != null){
                    mutableLiveData.setValue(addressResponse);
                }
            }

            @Override
            public void onFailure(Call<AddressResponse> call, Throwable t) {
             mutableLiveData.setValue(null);
            }
        });

        return mutableLiveData;
    }
}
