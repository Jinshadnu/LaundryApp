package com.example.laundryapp.core;

import com.example.laundryapp.register.pojo.user;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;

public interface NetworkAPI {

    @POST("register")
    Call<List<user>> userRegistration();
}
