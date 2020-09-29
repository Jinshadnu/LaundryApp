package com.example.laundryapp.login.repository;

import com.example.laundryapp.utilities.CommonResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class LoginRepository {

    public LoginRepository() {

    }

    public LiveData<CommonResponse> checkLogin(){
        MutableLiveData mutableLiveData=new MutableLiveData();

        return mutableLiveData;
    }

}
