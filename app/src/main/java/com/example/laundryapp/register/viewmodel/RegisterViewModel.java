package com.example.laundryapp.register.viewmodel;

import com.example.laundryapp.register.pojo.RegisterResponse;
import com.example.laundryapp.register.repository.RegisterRepository;
import com.example.laundryapp.utilities.CommonResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class RegisterViewModel extends ViewModel {
    public RegisterRepository registerRepository;

    public RegisterViewModel() {
        registerRepository=new RegisterRepository();
    }
    public LiveData<RegisterResponse> registerUser(String name, String phone, String email, String password){
        return registerRepository.userRegistration(name, phone, email, password);
    }
}
