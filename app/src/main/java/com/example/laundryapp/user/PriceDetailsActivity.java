package com.example.laundryapp.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.ActivityPriceDetailsBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PriceDetailsActivity extends AppCompatActivity {
   public ActivityPriceDetailsBinding priceDetailsBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_details);

        priceDetailsBinding= DataBindingUtil.setContentView(this,R.layout.activity_price_details);

        priceDetailsBinding.layoutBase.textTitle.setText("Price Details");

        priceDetailsBinding.layoutBase.toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });

        priceDetailsBinding.layoutBase.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);


        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        String thisDate = currentDate.format(todayDate);

        priceDetailsBinding.textTodayDate.setText(thisDate);

        String total_amount=getIntent().getStringExtra("price");
        String quantity=getIntent().getStringExtra("qauntity");

        priceDetailsBinding.textAmount.setText(total_amount);
        priceDetailsBinding.textOrderTotal.setText(total_amount);
        priceDetailsBinding.textItemscount.setText(quantity);



        priceDetailsBinding.buttonPickup.setOnClickListener(v -> {
            startActivity(new Intent(PriceDetailsActivity.this,AddressActivity.class));
        });
    }
}