package com.example.laundryapp.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.laundryapp.MainActivity;
import com.example.laundryapp.R;
import com.example.laundryapp.databinding.ActivityLoginBinding;
import com.example.laundryapp.login.viewmodel.LoginViewModel;
import com.example.laundryapp.register.RegisterActivity;
import com.example.laundryapp.user.ForgetPasswordActivity;
import com.example.laundryapp.user.HomeActivity;
import com.example.laundryapp.utilities.BaseActivity;
import com.example.laundryapp.utilities.Constants;
import com.example.laundryapp.utilities.NetworkUtilities;

import java.util.Objects;

import static android.text.TextUtils.isEmpty;
import static java.util.Objects.requireNonNull;

public class LoginActivity extends BaseActivity {

public ActivityLoginBinding loginBinding;
public String username,password;
public LoginViewModel loginViewModel;
public ProgressDialog progressDialog;
public int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        loginBinding= DataBindingUtil.setContentView(this,R.layout.activity_login);

        loginViewModel= ViewModelProviders.of(this).get(LoginViewModel.class);

        loginBinding.textViewSignup.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

        loginBinding.buttonLogin.setOnClickListener(v -> {
            if (validatefields()){
                checkLogin();
            }
        });
        loginBinding.textViewForget.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
        });


    }

    public boolean validatefields(){
        username= requireNonNull(loginBinding.edittextName.getText().toString().trim());
        password= requireNonNull(loginBinding.edittextPhone.getText().toString().trim());

        if (isEmpty(username)){
            loginBinding.edittextName.setError("please enter your name");
            return false;
        }

        if (isEmpty(password)){
            loginBinding.edittextPhone.setError("please enter password");
            return false;
        }

        return true;
    }

    public void checkLogin(){
        if (NetworkUtilities.getNetworkInstance(this).isConnectedToInternet()){
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Please wait");
            progressDialog.show();

            loginViewModel.checkLogin(username,password).observe(this,loginResponse -> {
                progressDialog.dismiss();
                if (loginResponse != null & loginResponse.getStatus().equals(Constants.SERVER_RESPONSE_SUCCESS)){
                    SharedPreferences sharedpreferences = getSharedPreferences(Constants.MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedpreferences.edit();
                    editor.putBoolean(Constants.IsUserLogIn, true);
                    editor.putString(Constants.USER_ID,loginResponse.getUser().get(position).getUser_id());
                    editor.putString(Constants.USER_NAME,loginResponse.getUser().get(position).getUsername());
                    editor.putString(Constants.PHONE,loginResponse.getUser().get(position).getPhone());
                    editor.putString(Constants.EMAIL,loginResponse.getUser().get(position).getEmail());
                    editor.commit();
                    startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                    finish();
                }
                else {
                    showSnackBar(this,"Invalid username or password");
                }

            });

        }
        else {
            showErrorSnackBar(this,getString(R.string.no_internet_message));
        }
    }
}