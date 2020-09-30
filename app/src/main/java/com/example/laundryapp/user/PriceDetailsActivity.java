package com.example.laundryapp.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.ActivityPriceDetailsBinding;

public class PriceDetailsActivity extends AppCompatActivity {
   public ActivityPriceDetailsBinding priceDetailsBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_details);

        priceDetailsBinding= DataBindingUtil.setContentView(this,R.layout.activity_price_details);

        priceDetailsBinding.layoutBase.textTitle.setText("Price Details");

        priceDetailsBinding.layoutBase.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);


        priceDetailsBinding.buttonPickup.setOnClickListener(v -> {
            startActivity(new Intent(PriceDetailsActivity.this,AddressActivity.class));
        });
    }
}