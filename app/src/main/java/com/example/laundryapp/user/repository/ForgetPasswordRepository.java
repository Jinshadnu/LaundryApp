package com.example.laundryapp.user.repository;

import com.example.laundryapp.core.NetworkAPI;
import com.example.laundryapp.core.NetworkService;
import com.example.laundryapp.utilities.CommonResponse;
import com.example.laundryapp.utilities.Constants;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordRepository {
    public NetworkAPI networkAPI;


    public ForgetPasswordRepository() {
    }

    public LiveData<CommonResponse> forgetPassword(String email){
        MutableLiveData mutableLiveData=new MutableLiveData();

        networkAPI= NetworkService.getRetrofitInstance().create(NetworkAPI.class);

        Call<CommonResponse> responseCall=networkAPI.forgetPassword(email);

        responseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                CommonResponse commonResponse=response.body();
                if (commonResponse != null && commonResponse.getStatus().equals(Constants.SERVER_RESPONSE_SUCCESS)){
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
