package com.example.laundryapp.user.viewModel;

import com.example.laundryapp.user.repository.ChangePasswordRepository;
import com.example.laundryapp.utilities.CommonResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class ChangePasswordViewModel extends ViewModel {
    public ChangePasswordRepository changePasswordRepository;

    public ChangePasswordViewModel() {
        this.changePasswordRepository=new ChangePasswordRepository();
    }

    public LiveData<CommonResponse> changePassword(int id,String oldPassword,String newPassword){
        return changePasswordRepository.changePassword(id,oldPassword,newPassword);
    }
}
