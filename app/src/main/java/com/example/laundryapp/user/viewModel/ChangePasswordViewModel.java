package com.example.laundryapp.user.viewModel;

import com.example.laundryapp.user.repository.ChangePasswordRepository;
import com.example.laundryapp.user.response.ComonResponse;
import com.example.laundryapp.utilities.CommonResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class ChangePasswordViewModel extends ViewModel {
    public ChangePasswordRepository changePasswordRepository;

    public ChangePasswordViewModel() {
        this.changePasswordRepository=new ChangePasswordRepository();
    }

    public LiveData<ComonResponse> changePassword(String id, String oldPassword, String newPassword){
        return changePasswordRepository.changePassword(id,oldPassword,newPassword);
    }
}
