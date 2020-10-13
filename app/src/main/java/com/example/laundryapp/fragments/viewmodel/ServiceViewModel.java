package com.example.laundryapp.fragments.viewmodel;

import com.example.laundryapp.fragments.pojo.Services;
import com.example.laundryapp.fragments.repository.ServiceRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class ServiceViewModel extends ViewModel {
public ServiceRepository serviceRepository;
    public ServiceViewModel() {
        this.serviceRepository=new ServiceRepository();
    }


    public LiveData<List<Services>> getServices(){
        return serviceRepository.getServices();
    }


}
