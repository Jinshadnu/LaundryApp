package com.example.laundryapp.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.ActivityAboutUsBinding;

public class AboutUsActivity extends AppCompatActivity {
public ActivityAboutUsBinding aboutUsBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_about_us);

        aboutUsBinding= DataBindingUtil.setContentView(this,R.layout.activity_about_us);

        aboutUsBinding.layoutBase.textTitle.setText("Contact us");

        aboutUsBinding.layoutBase.toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });

        aboutUsBinding.layoutBase.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);








    }
}