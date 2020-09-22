package com.example.laundryapp.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.ActivitySuccessBinding;

public class SuccessActivity extends AppCompatActivity {
public ActivitySuccessBinding successBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        successBinding= DataBindingUtil.setContentView(this,R.layout.activity_success);

        successBinding.buttonBack.setOnClickListener(v -> {
            startActivity(new Intent(SuccessActivity.this,HomeActivity.class));
        });
    }
}