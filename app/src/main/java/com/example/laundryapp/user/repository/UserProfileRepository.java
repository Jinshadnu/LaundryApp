package com.example.laundryapp.user.repository;

import android.widget.ScrollView;

import com.example.laundryapp.core.NetworkAPI;
import com.example.laundryapp.core.NetworkService;
import com.example.laundryapp.user.pojo.UserResponse;
import com.example.laundryapp.user.response.ComonResponse;
import com.example.laundryapp.utilities.NetworkUtilities;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileRepository {
    public NetworkAPI networkAPI;
    public UserProfileRepository() {

    }

    public LiveData<UserResponse> getUsers(String userId){
        MutableLiveData mutableLiveData=new MutableLiveData();

        networkAPI= NetworkService.getRetrofitInstance().create(NetworkAPI.class);

        Call<UserResponse> responseCall=networkAPI.getuserDetails(userId);

        responseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse userResponse=response.body();
                if (userResponse != null){
                    mutableLiveData.setValue(userResponse);
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
             mutableLiveData.setValue(null);
            }
        });

        return mutableLiveData;

    }

    public LiveData<ComonResponse> editProfile(String userId,String phone,String email){
        MutableLiveData mutableLiveData=new MutableLiveData();

        networkAPI= NetworkService.getRetrofitInstance().create(NetworkAPI.class);

        Call<ComonResponse> responseCall=networkAPI.editProfile(userId,phone,email);
        responseCall.enqueue(new Callback<ComonResponse>() {
            @Override
            public void onResponse(Call<ComonResponse> call, Response<ComonResponse> response) {
                ComonResponse comonResponse=response.body();
                if (comonResponse != null){
                    mutableLiveData.postValue(comonResponse);
                }
            }

            @Override
            public void onFailure(Call<ComonResponse> call, Throwable t) {
             mutableLiveData.postValue(null);
            }
        });
        return mutableLiveData;
    }
 }
