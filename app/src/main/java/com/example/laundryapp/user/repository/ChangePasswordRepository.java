package com.example.laundryapp.user.repository;

import com.example.laundryapp.core.NetworkAPI;
import com.example.laundryapp.core.NetworkService;
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

    public LiveData<CommonResponse> changePassword(int id,String oldPassword,String newPassword){
        MutableLiveData mutableLiveData=new MutableLiveData();

        networkAPI= NetworkService.getRetrofitInstance().create(NetworkAPI.class);
        Call<CommonResponse> responseCall=networkAPI.forgetPassword(id,oldPassword,newPassword);

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
