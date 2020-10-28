package com.example.laundryapp.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.ActivityChangePasswordBinding;
import com.example.laundryapp.user.viewModel.ChangePasswordViewModel;
import com.example.laundryapp.utilities.BaseActivity;
import com.example.laundryapp.utilities.Constants;
import com.example.laundryapp.utilities.NetworkUtilities;

import static android.text.TextUtils.isEmpty;
import static java.util.Objects.requireNonNull;

public class ChangePassword extends BaseActivity {
    public ChangePasswordViewModel passwordViewModel;
    public ProgressDialog progressDialog;
    public ActivityChangePasswordBinding changePasswordBinding;
    public String oldPassword,newPassword,confirmPassword;


    public int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
       // setContentView(R.layout.activity_change_password);

        changePasswordBinding= DataBindingUtil.setContentView(this,R.layout.activity_change_password);

        SharedPreferences sharedPreferences=getSharedPreferences(Constants.MyPREFERENCES,MODE_PRIVATE);

        id=sharedPreferences.getInt(Constants.USER_ID,0);



        passwordViewModel= ViewModelProviders.of(this).get(ChangePasswordViewModel.class);

        changePasswordBinding.buttonSave.setOnClickListener(v -> {
            if (validatefield()){
                changePassword();
            }
        });




    }

    public boolean validatefield(){
        oldPassword= requireNonNull(changePasswordBinding.edittextOldpassword.getText().toString().trim());
        newPassword= requireNonNull(changePasswordBinding.edittextNewPassword.getText().toString().trim());
        confirmPassword=requireNonNull(changePasswordBinding.editTextConfirmPassword.getText().toString().trim());

        if (isEmpty(oldPassword)){
            changePasswordBinding.edittextOldpassword.setError("please enter your password");
            return false;
        }

        if (isEmpty(newPassword)){
            changePasswordBinding.edittextNewPassword.setError("please enter your new password");
            return false;
        }

        if (isEmpty(confirmPassword)){
            changePasswordBinding.editTextConfirmPassword.setError("please enter your new password");
            return false;
        }

        if (newPassword != confirmPassword){
            changePasswordBinding.editTextConfirmPassword.setError("please confirm your new password");
            return false;
        }

        return true;
    }
    public void changePassword(){
        if (NetworkUtilities.getNetworkInstance(this).isConnectedToInternet()){
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Loading....");
            progressDialog.show();

            passwordViewModel.changePassword(id,oldPassword,confirmPassword).observe(this,commonResponse -> {
                progressDialog.dismiss();

                if (commonResponse != null && commonResponse.getStatus().equals(Constants.SERVER_RESPONSE_SUCCESS)){
                    openSuccessDialog(commonResponse.getError_message());
                }
            });
        }
    }

}