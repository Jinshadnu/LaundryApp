package com.example.laundryapp.core;

import com.example.laundryapp.login.pojo.LoginResponse;
import com.example.laundryapp.register.pojo.user;
import com.example.laundryapp.utilities.CommonResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface NetworkAPI {

    @FormUrlEncoded
    @POST("Api_1.php?apicall=signup")
    Call<CommonResponse> userRegistration(@Field("name") String name,@Field("phone")String phone,@Field("email")String email,@Field("password")String password);

    @FormUrlEncoded
    @POST("Api_1.php?apicall=login")
    Call<LoginResponse> userLogin(@Field("email") String email, @Field("password")String password);

    @FormUrlEncoded
    @POST("Api_1.php?apicall=forgot_password")
    Call<CommonResponse> forgetPassword(@Field("email") String email);

    @FormUrlEncoded
    @POST("Api_1.php?apicall=change_password")
    Call<CommonResponse> forgetPassword(@Field("id") int id,@Field("oldPassword") String oldPassword,@Field("newPassword")String newPassword);




}
