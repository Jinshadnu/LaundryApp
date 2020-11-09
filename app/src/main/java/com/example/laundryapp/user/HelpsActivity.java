package com.example.laundryapp.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.ActivityHelpsBinding;

public class HelpsActivity extends AppCompatActivity {
public ActivityHelpsBinding helpsBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_helps);

        helpsBinding= DataBindingUtil.setContentView(this,R.layout.activity_helps);

        helpsBinding.layoutBase.textTitle.setText("Helps");

        helpsBinding.layoutBase.toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });

        helpsBinding.layoutBase.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);





    }
}