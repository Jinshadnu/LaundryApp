package com.example.laundryapp.user.repository;

import com.example.laundryapp.core.NetworkAPI;
import com.example.laundryapp.core.NetworkService;
import com.example.laundryapp.user.pojo.ServiceResponse;
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

    public LiveData<ServiceResponse> getService(){
        MutableLiveData mutableLiveData=new MutableLiveData();

        networkAPI= NetworkService.getRetrofitInstance().create(NetworkAPI.class);

        Call<ServiceResponse> responseCall=networkAPI.getServices();

        responseCall.enqueue(new Callback<ServiceResponse>() {
            @Override
            public void onResponse(Call<ServiceResponse> call, Response<ServiceResponse> response) {
                ServiceResponse serviceResponse=response.body();

                if (serviceResponse != null){
                    mutableLiveData.setValue(serviceResponse);
                }
            }

            @Override
            public void onFailure(Call<ServiceResponse> call, Throwable t) {
              mutableLiveData.setValue(null);
            }
        });



        return mutableLiveData;
    }


}
