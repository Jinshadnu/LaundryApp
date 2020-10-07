package com.example.laundryapp.user.repository;

import com.example.laundryapp.core.NetworkAPI;
import com.example.laundryapp.core.NetworkService;
import com.example.laundryapp.utilities.CommonResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceRepository  {
    public NetworkAPI networkAPI;
    public ServiceRepository() {

    }

    public LiveData<CommonResponse> getService(){
        MutableLiveData mutableLiveData=new MutableLiveData();

        networkAPI= NetworkService.getRetrofitInstance().create(NetworkAPI.class);

        Call<CommonResponse> responseCall=networkAPI.getServices();

        responseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                CommonResponse commonResponse=response.body();

                if (commonResponse != null){
                    mutableLiveData.setValue(commonResponse);
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
              mutableLiveData.setValue(null);
            }
        });



        return mutableLiveData;
    }
}
