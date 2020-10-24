package com.example.laundryapp.core;

import com.example.laundryapp.login.pojo.LoginResponse;
import com.example.laundryapp.register.pojo.user;
import com.example.laundryapp.user.response.AddressResponse;
import com.example.laundryapp.user.response.CartResponse;
import com.example.laundryapp.user.response.OrderResponse;
import com.example.laundryapp.utilities.CommonResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NetworkAPI {

    @FormUrlEncoded
    @POST("Api_1.php?apicall=signup")
    Call<CommonResponse> userRegistration(@Field("name") String name,@Field("phone")String phone,@Field("email")String email,@Field("password")String password);

    @FormUrlEncoded
    @POST("Api_1.php?apicall=login")
    Call<LoginResponse> userLogin(@Field("email") String email, @Field("password")String password);

    @FormUrlEncoded
    @POST("Api_1.php?apicall=forgot")
    Call<CommonResponse> forgetPassword(@Field("email") String email);

    @FormUrlEncoded
    @POST("Api_1.php?apicall=change_password")
    Call<CommonResponse> forgetPassword(@Field("id") int id,@Field("oldPassword") String oldPassword,@Field("newPassword")String newPassword);

    @GET("getservice")
    Call<CommonResponse> getServices();

    @FormUrlEncoded
    @POST("addToCart")
    Call<CommonResponse> addToCart(@Field("user_id")int id,@Field("service_name")String service_name,@Field("item_id")int item_id,@Field("quantity")int quantity,@Field("price") int price);

    @FormUrlEncoded
    @POST("addaddress")
    Call<CommonResponse> addAddress(@Field("user_id")int id,@Field("building_number")String number,@Field("street_adress") String address,@Field("zone_number")int zone);

    @GET("getCart")
    Call<CartResponse> getCartItems(@Query("user_id") int user_id);

    @FormUrlEncoded
    @POST("addOrder")
    Call<CommonResponse> addOrder(@Field("user_id") int user_id,@Field("service_name")String service_name,@Field("item_name")String item_name,@Field("total_price")int total_price,@Field("address") String address);

    @GET("getaddress")
    Call<AddressResponse> getAddress(@Query("user_id")int user_id);

    @GET("getOrders")
    Call<OrderResponse> getOrders(@Query("user_id")int user_id);






}
