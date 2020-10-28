package com.example.laundryapp.fragments.repository;

import com.example.laundryapp.R;
import com.example.laundryapp.core.NetworkAPI;
import com.example.laundryapp.core.NetworkService;
import com.example.laundryapp.fragments.pojo.Items;
import com.example.laundryapp.fragments.pojo.Services;
import com.example.laundryapp.user.pojo.ServiceResponse;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceRepository {
    public NetworkAPI networkAPI;
    public ServiceRepository() {
    }

    public LiveData<List<Services>> getServices(){
        MutableLiveData mutableLiveData=new MutableLiveData();

        List<Services> servicesList=new ArrayList<>();
        servicesList.add(new Services("Wash and Iron",R.drawable.washandiron));
        servicesList.add(new Services("Pressing", R.drawable.steam_pressing));
        servicesList.add(new Services("Dry Cleaning",R.drawable.drycleaning));
        servicesList.add(new Services("Urgent", R.drawable.urgent));

        mutableLiveData.setValue(servicesList);

        return mutableLiveData;
    }

    public LiveData<ServiceResponse> fetchService(){
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
