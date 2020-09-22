package com.example.laundryapp.user;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.ActivityAddressBinding;
import com.example.laundryapp.databinding.ActivityOrderBinding;

import static java.util.Objects.requireNonNull;

public class AddressActivity extends AppCompatActivity {
public ActivityAddressBinding addressBinding;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        addressBinding= DataBindingUtil.setContentView(this,R.layout.activity_address);

        addressBinding.buttonsubmit.setOnClickListener(v -> {
            startActivity(new Intent(AddressActivity.this,SuccessActivity.class));
        });

        addressBinding.cardViewHome.setOnClickListener(v -> {
            addressBinding.cardViewHome.setBackground(requireNonNull(this)
                    .getDrawable(R.color.colorDarkstateBlue));
            addressBinding.cardViewOffice.setBackground(requireNonNull(this)
                    .getDrawable(R.color.colorwhite));
            addressBinding.textHome.setTextColor(getResources().getColor(R.color.colorwhite));
            addressBinding.textOffice.setTextColor(getResources().getColor(R.color.colorblack));
            addressBinding.imageViewHome.setBackground(requireNonNull(this)
                    .getDrawable(R.color.colorwhite));
            addressBinding.imageViewHome.setBackground(requireNonNull(this)
                    .getDrawable(R.color.colorDarkstateBlue));
        });

        addressBinding.cardViewOffice.setOnClickListener(v -> {
            addressBinding.cardViewHome.setBackground(requireNonNull(this)
                    .getDrawable(R.color.colorwhite));
            addressBinding.cardViewOffice.setBackground(requireNonNull(this)
                    .getDrawable(R.color.colorDarkstateBlue));
            addressBinding.textHome.setTextColor(getResources().getColor(R.color.colorblack));
            addressBinding.textOffice.setTextColor(getResources().getColor(R.color.colorwhite));
            addressBinding.imageViewOffice.setBackground(requireNonNull(this)
                    .getDrawable(R.color.colorwhite));
            addressBinding.imageViewHome.setBackground(requireNonNull(this)
                    .getDrawable(R.color.colorDarkstateBlue));
        });


    }
}