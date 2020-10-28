package com.example.laundryapp.user.viewModel;

import com.example.laundryapp.user.pojo.ServiceResponse;
import com.example.laundryapp.user.repository.ServiceRepository;
import com.example.laundryapp.utilities.CommonResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class ServiceViewModel extends ViewModel {

    public ServiceRepository serviceRepository;

    public ServiceViewModel() {
        this.serviceRepository=new ServiceRepository();
    }

    public LiveData<ServiceResponse> getService(){
        return serviceRepository.getService();
    }
}
