package com.example.laundryapp.fragments.repository;

import com.example.laundryapp.R;
import com.example.laundryapp.fragments.pojo.Items;
import com.example.laundryapp.fragments.pojo.Services;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ServiceRepository {
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
}
