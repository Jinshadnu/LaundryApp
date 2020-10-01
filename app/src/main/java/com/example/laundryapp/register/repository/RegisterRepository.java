package com.example.laundryapp.register.repository;

import com.example.laundryapp.core.NetworkAPI;
import com.example.laundryapp.core.NetworkService;
import com.example.laundryapp.register.pojo.user;
import com.example.laundryapp.utilities.CommonResponse;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterRepository {
    public NetworkAPI networkAPI;

    public RegisterRepository() {
    }

    public LiveData<CommonResponse> userRegistration(String name,String phone,String email,String password){
        MutableLiveData mutableLiveData=new MutableLiveData();

        networkAPI= NetworkService.getRetrofitInstance().create(NetworkAPI.class);
        Call<CommonResponse> responseCall=networkAPI.userRegistration(name, phone, email, password);
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
}
