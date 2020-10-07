package com.example.laundryapp.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.laundryapp.R;
import com.example.laundryapp.user.viewModel.ChangePasswordViewModel;
import com.example.laundryapp.utilities.BaseActivity;
import com.example.laundryapp.utilities.Constants;
import com.example.laundryapp.utilities.NetworkUtilities;

public class ChangePassword extends BaseActivity {
    public ChangePasswordViewModel passwordViewModel;
    public ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_change_password);

        passwordViewModel= ViewModelProviders.of(this).get(ChangePasswordViewModel.class);


    }

    public void changePassword(){
        if (NetworkUtilities.getNetworkInstance(this).isConnectedToInternet()){
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Loading....");
            progressDialog.show();

            passwordViewModel.changePassword(1,"","").observe(this,commonResponse -> {
                progressDialog.dismiss();

                if (commonResponse != null && commonResponse.getStatus().equals(Constants.SERVER_RESPONSE_SUCCESS)){
                    openSuccessDialog(commonResponse.getMessage());
                }
            });
        }
    }

}