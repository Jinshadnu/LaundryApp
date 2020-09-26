package com.example.laundryapp.register.viewmodel;

import com.example.laundryapp.register.repository.RegisterRepository;

import androidx.lifecycle.ViewModel;

public class RegisterViewModel extends ViewModel {
    public RegisterRepository registerRepository;

    public RegisterViewModel() {
        registerRepository=new RegisterRepository();
    }
}
