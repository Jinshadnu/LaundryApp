package com.example.laundryapp.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.ActivityRegisterBinding;
import com.example.laundryapp.login.LoginActivity;
import com.example.laundryapp.user.HomeActivity;

public class RegisterActivity extends AppCompatActivity {
public ActivityRegisterBinding registerBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        registerBinding= DataBindingUtil.setContentView(this,R.layout.activity_register);

        registerBinding.buttonRegister.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
        });

        registerBinding.textViewSignIn.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });


    }
}