package com.example.laundryapp.register.repository;

import com.example.laundryapp.register.pojo.user;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class RegisterRepository {

    public RegisterRepository() {
    }

    public LiveData<List<user>> userRegistration(){
        MutableLiveData mutableLiveData=new MutableLiveData();


        return mutableLiveData;
    }
}
