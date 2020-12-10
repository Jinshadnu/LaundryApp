package com.example.laundryapp.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.ActivityPriceDetailsBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PriceDetailsActivity extends AppCompatActivity {
   public ActivityPriceDetailsBinding priceDetailsBinding;
   public double aDouble;
   public double amount;
   public int orderType=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_details);

        priceDetailsBinding= DataBindingUtil.setContentView(this,R.layout.activity_price_details);

        priceDetailsBinding.layoutBase.textTitle.setText("Price Details");

        priceDetailsBinding.layoutBase.toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });





        priceDetailsBinding.layoutBase.imageViewCart.setOnClickListener(v -> {
            startActivity(new Intent(PriceDetailsActivity.this,CartActivity.class));
        });

        priceDetailsBinding.layoutBase.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);


        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        String thisDate = currentDate.format(todayDate);

        priceDetailsBinding.textTodayDate.setText(thisDate);

        String total_amount=getIntent().getStringExtra("price");
        String quantity=getIntent().getStringExtra("qauntity");
        aDouble=getIntent().getDoubleExtra("amount",0.00);

        priceDetailsBinding.textAmount.setText(total_amount);
        priceDetailsBinding.textItemscount.setText(quantity);
        priceDetailsBinding.txtAmount.setText("QAR: " + total_amount);

        priceDetailsBinding.checkBoxUrgent.setOnCheckedChangeListener((compoundButton, b) -> {
            if(priceDetailsBinding.checkBoxUrgent.isChecked()){
                priceDetailsBinding.textUrgentMessage.setVisibility(View.VISIBLE);
                amount=aDouble * 2;
                priceDetailsBinding.txtAmount.setText("QAR: " + String.valueOf(amount));
                orderType=1;

            }
            else {
                priceDetailsBinding.textUrgentMessage.setVisibility(View.GONE);
                priceDetailsBinding.txtAmount.setText("QAR: " + total_amount);
                orderType=0;
            }
        });

        priceDetailsBinding.buttonPickup.setOnClickListener(v -> {
            Intent intent=new Intent(PriceDetailsActivity.this,AddressActivity.class);
            intent.putExtra("total_amount",priceDetailsBinding.txtAmount.getText().toString());
            intent.putExtra("order_type",orderType);
            startActivity(intent);
        });
    }
}