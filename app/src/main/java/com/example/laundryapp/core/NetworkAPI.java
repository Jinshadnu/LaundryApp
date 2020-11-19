package com.example.laundryapp.core;

import com.example.laundryapp.login.pojo.LoginResponse;
import com.example.laundryapp.register.pojo.RegisterResponse;
import com.example.laundryapp.user.pojo.ServiceResponse;
import com.example.laundryapp.user.response.AddressResponse;
import com.example.laundryapp.user.response.CartResponse;
import com.example.laundryapp.user.response.ComonResponse;
import com.example.laundryapp.user.response.OrderResponse;
import com.example.laundryapp.user.response.UpdateResponse;
import com.example.laundryapp.utilities.CommonResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NetworkAPI {

    @FormUrlEncoded
    @POST("register")
    Call<RegisterResponse> userRegistration(@Field("name") String name, @Field("phone")String phone, @Field("email")String email, @Field("password")String password);

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> userLogin(@Field("phone") String phone, @Field("password")String password);

    @GET("get_service")
    Call<ServiceResponse> getServices();

    @FormUrlEncoded
    @POST("change_password")
    Call<ComonResponse> changePassword(@Field("user_id") String id, @Field("old_password") String oldPassword, @Field("new_password")String newPassword);


    @FormUrlEncoded
    @POST("forgot_password")
    Call<ComonResponse> forgetPassword(@Field("email") String email);


    @FormUrlEncoded
    @POST("add_to_cart")
    Call<ComonResponse> addToCart(@Field("user_id")String id,@Field("service_name")String service_name,@Field("item_id")String item_id,@Field("quantity")String quantity,@Field("price") String price);

    @GET("get_cart")
    Call<CartResponse> getCartItems(@Query("user_id") String user_id);

    @FormUrlEncoded
    @POST("add_address")
    Call<ComonResponse> addAddress(@Field("user_id")String id,@Field("building_number")String number,@Field("street_address") String address,@Field("zone_number")String zone);


    @FormUrlEncoded
    @POST("add_order")
    Call<ComonResponse> addOrder(@Field("user_id") String user_id,@Field("building_number")String buliding_number,@Field("street_number") String street_number,@Field("zone")String zone,@Field("latitude")String latitude,@Field("longitude")String longitude,@Field("address")String address);

    @GET("get_address")
    Call<AddressResponse> getAddress(@Query("user_id")String user_id);

    @GET("get_orders")
    Call<OrderResponse> getOrders(@Query("user_id")String user_id);

    @FormUrlEncoded
    @POST("delete_cart_item")
    Call<ComonResponse> deleteCartItem(@Field("user_id")String user_id,@Field("item_id")String item_id);

    @FormUrlEncoded
    @POST("update_count")
    Call<UpdateResponse> updateCartItem(@Field("item_id")String item_id, @Field("user_id")String user_id, @Field("quantity")String quantity,@Field("price")String price);







}
