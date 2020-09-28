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

import static android.text.TextUtils.isEmpty;
import static java.util.Objects.requireNonNull;

public class RegisterActivity extends AppCompatActivity {
public ActivityRegisterBinding registerBinding;
   public boolean isPasswordShown = false;
   public String userName,password,phone,email,confirm_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        registerBinding= DataBindingUtil.setContentView(this,R.layout.activity_register);

        registerBinding.buttonRegister.setOnClickListener(v -> {
            if (validateFields()){
                startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
            }
        });

        registerBinding.textViewSignIn.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });


    }

    private boolean validateFields() {
        userName = requireNonNull(registerBinding.edittextName.getText()).toString().trim();
        phone = requireNonNull(registerBinding.edittextPhone.getText()).toString().trim();
        email = requireNonNull(registerBinding.editextEmail.getText()).toString().trim();
        password = requireNonNull(registerBinding.editextPassword.getText()).toString().trim();
        confirm_password=requireNonNull(registerBinding.edittextConfirm.getText()).toString().trim();




        if (isEmpty(userName)) {
            registerBinding.edittextName.setError("Please enter your name");
            return false;
        }

        if (isEmpty(phone)) {
            registerBinding.edittextPhone.setError("Please enter phone number");
            return false;
        }

        if (isEmpty(email)) {
            registerBinding.editextEmail.setError("Please enter email");
            return false;
        }

        if (isEmpty(password)) {
            registerBinding.editextPassword.setError("Please enter password");
            return false;
        }

        if (isEmpty(confirm_password)) {
            registerBinding.edittextConfirm.setError("Please enter password");
            return false;
        }

        return true;
    }
}