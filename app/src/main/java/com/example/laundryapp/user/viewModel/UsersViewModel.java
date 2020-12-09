package com.example.laundryapp.user.viewModel;

import com.example.laundryapp.user.pojo.UserResponse;
import com.example.laundryapp.user.repository.UserProfileRepository;
import com.example.laundryapp.user.response.ComonResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class UsersViewModel extends ViewModel {
    public UserProfileRepository profileRepository;

    public UsersViewModel() {
        this.profileRepository=new UserProfileRepository();
    }

    public LiveData<UserResponse> getUsers(String userId){
        return profileRepository.getUsers(userId);
    }

    public LiveData<ComonResponse> editProfile(String userId,String phone,String email){
        return profileRepository.editProfile(userId,phone,email);
    }
}
