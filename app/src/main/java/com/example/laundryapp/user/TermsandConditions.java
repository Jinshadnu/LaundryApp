package com.example.laundryapp.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.ActivityTermsandConditionsBinding;

public class TermsandConditions extends AppCompatActivity {
    public ActivityTermsandConditionsBinding termsandConditionsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termsand_conditions);

        termsandConditionsBinding= DataBindingUtil.setContentView(this,R.layout.activity_termsand_conditions);

        termsandConditionsBinding.layoutBase.textTitle.setText("Terms and Conditions");

        termsandConditionsBinding.layoutBase.toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });

        termsandConditionsBinding.layoutBase.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);

    }
}