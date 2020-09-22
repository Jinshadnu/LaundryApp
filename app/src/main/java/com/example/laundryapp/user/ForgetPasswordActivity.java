package com.example.laundryapp.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.ActivityForgetPasswordBinding;
import com.example.laundryapp.utilities.BaseActivity;

public class ForgetPasswordActivity extends BaseActivity {
public ActivityForgetPasswordBinding forgetPasswordBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        forgetPasswordBinding= DataBindingUtil.setContentView(this,R.layout.activity_forget_password);

        forgetPasswordBinding.buttonSbumit.setOnClickListener(v -> {
            openSuccessDialog("Success");
        });
    }
}