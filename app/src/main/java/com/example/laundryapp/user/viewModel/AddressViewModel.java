package com.example.laundryapp.user.viewModel;

import com.example.laundryapp.user.repository.AddressRepository;
import com.example.laundryapp.user.response.AddressResponse;
import com.example.laundryapp.utilities.CommonResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class AddressViewModel extends ViewModel {
    public AddressRepository addressRepository;

    public AddressViewModel() {
       this.addressRepository=new AddressRepository();
    }

    public LiveData<CommonResponse> addAddress(int id,String building_number,String address,int zone){
        return addressRepository.addAddress(id,building_number,address,zone);
    }


    public LiveData<AddressResponse> getAddress(int user_id){
        return addressRepository.getAddress(user_id);
    }
}
