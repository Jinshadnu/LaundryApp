package com.example.laundryapp.user.viewModel;

import com.example.laundryapp.user.repository.ForgetPasswordRepository;
import com.example.laundryapp.user.response.ComonResponse;
import com.example.laundryapp.utilities.CommonResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class ForgotViewModel extends ViewModel {
    public ForgetPasswordRepository forgetPasswordRepository;

    public ForgotViewModel() {
        this.forgetPasswordRepository=new ForgetPasswordRepository();
    }

    public LiveData<ComonResponse> forgotPassword(String email){
        return forgetPasswordRepository.forgetPassword(email);
    }
}
