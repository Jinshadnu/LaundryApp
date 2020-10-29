package com.example.laundryapp.login.repository;

import com.example.laundryapp.core.NetworkAPI;
import com.example.laundryapp.core.NetworkService;
import com.example.laundryapp.login.pojo.LoginResponse;
import com.example.laundryapp.utilities.CommonResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
public NetworkAPI networkAPI;
    public LoginRepository() {

    }

    public LiveData<LoginResponse> checkLogin(String phone,String password){
        MutableLiveData mutableLiveData=new MutableLiveData();

        networkAPI= NetworkService.getRetrofitInstance().create(NetworkAPI.class);

        Call<LoginResponse> responseCall=networkAPI.userLogin(phone,password);

        responseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse=response.body();
                if (loginResponse != null){
                    mutableLiveData.postValue(loginResponse);
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
             mutableLiveData.postValue(null);
            }
        });


        return mutableLiveData;
    }

}
