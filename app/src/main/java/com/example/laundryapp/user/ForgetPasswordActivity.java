package com.example.laundryapp.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.ActivityForgetPasswordBinding;
import com.example.laundryapp.user.viewModel.ForgotViewModel;
import com.example.laundryapp.utilities.BaseActivity;
import com.example.laundryapp.utilities.Constants;
import com.example.laundryapp.utilities.NetworkUtilities;

public class ForgetPasswordActivity extends BaseActivity {
public ActivityForgetPasswordBinding forgetPasswordBinding;
public ForgotViewModel forgotViewModel;
public ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        forgetPasswordBinding= DataBindingUtil.setContentView(this,R.layout.activity_forget_password);

        forgotViewModel= ViewModelProviders.of(this).get(ForgotViewModel.class);


        forgetPasswordBinding.buttonSbumit.setOnClickListener(v -> {
            openSuccessDialog("Success");
        });


    }

    public void forgetPassword(){
        if (NetworkUtilities.getNetworkInstance(this).isConnectedToInternet()){
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();

            forgotViewModel.forgotPassword(forgetPasswordBinding.edittextEmail.getText().toString()).observe(this,commonResponse -> {
                progressDialog.dismiss();

                if(commonResponse != null && commonResponse.getStatus().equals(Constants.SERVER_RESPONSE_SUCCESS)){
                    openSuccessDialog(commonResponse.getMessage());
                }
            });


        }
    }
}