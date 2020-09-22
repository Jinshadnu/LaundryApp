package com.example.laundryapp.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.laundryapp.MainActivity;
import com.example.laundryapp.R;
import com.example.laundryapp.databinding.ActivityLoginBinding;
import com.example.laundryapp.register.RegisterActivity;
import com.example.laundryapp.user.ForgetPasswordActivity;
import com.example.laundryapp.user.HomeActivity;

public class LoginActivity extends AppCompatActivity {
public ActivityLoginBinding loginBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        loginBinding= DataBindingUtil.setContentView(this,R.layout.activity_login);

        loginBinding.textViewSignup.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

        loginBinding.buttonLogin.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        });
        loginBinding.textViewForget.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
        });


    }
}