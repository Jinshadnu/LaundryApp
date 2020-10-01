package com.example.laundryapp.login.viewmodel;

import com.example.laundryapp.login.pojo.LoginResponse;
import com.example.laundryapp.login.repository.LoginRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {
    public LoginRepository loginRepository;


    public LoginViewModel() {
        this.loginRepository=new LoginRepository();
    }

    public LiveData<LoginResponse> checkLogin(String name,String password){
        return loginRepository.checkLogin(name,password);
    }

}
