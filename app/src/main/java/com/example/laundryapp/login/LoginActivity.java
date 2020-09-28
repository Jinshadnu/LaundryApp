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

import java.util.Objects;

import static android.text.TextUtils.isEmpty;
import static java.util.Objects.requireNonNull;

public class LoginActivity extends AppCompatActivity {
public ActivityLoginBinding loginBinding;
public String username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        loginBinding= DataBindingUtil.setContentView(this,R.layout.activity_login);

        loginBinding.textViewSignup.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

        loginBinding.buttonLogin.setOnClickListener(v -> {
            if (validatefields()){
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
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
}