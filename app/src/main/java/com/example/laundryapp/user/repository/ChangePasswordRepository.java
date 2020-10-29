package com.example.laundryapp.user.repository;

import com.example.laundryapp.core.NetworkAPI;
import com.example.laundryapp.core.NetworkService;
import com.example.laundryapp.user.response.ComonResponse;
import com.example.laundryapp.utilities.CommonResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordRepository {
    public NetworkAPI networkAPI;

    public ChangePasswordRepository() {
    }

    public LiveData<ComonResponse> changePassword(String id,String oldPassword,String newPassword){
        MutableLiveData mutableLiveData=new MutableLiveData();

        networkAPI= NetworkService.getRetrofitInstance().create(NetworkAPI.class);
        Call<ComonResponse> responseCall=networkAPI.changePassword(id,oldPassword,newPassword);

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
}
