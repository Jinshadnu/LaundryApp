package com.example.laundryapp.login.viewmodel;

import com.example.laundryapp.login.repository.LoginRepository;

public class LoginViewModel {
    public LoginRepository loginRepository;


    public LoginViewModel() {
        this.loginRepository=new LoginRepository();
    }

}
