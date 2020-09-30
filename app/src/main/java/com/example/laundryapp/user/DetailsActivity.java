package com.example.laundryapp.user;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


import com.example.laundryapp.R;

import com.example.laundryapp.fragments.adapter.ServiceImageAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    private ViewPager productImageViewPager;
    private TabLayout viewpagerIndicator;
    public Toolbar toolbar;
    public TextView textView;
    public Button button_order;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        button_order=(Button)findViewById(R.id.button_order);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        textView=(TextView) findViewById(R.id.text_title);

        textView.setText("Service Details");

//        toolbar.setTitle("Service Details");

        setSupportActionBar(toolbar);

        //toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);






        button_order.setOnClickListener(v -> {
            startActivity(new Intent(DetailsActivity.this,OrderActivity.class));
        });


        productImageViewPager=findViewById(R.id.viewPager2);
        viewpagerIndicator=findViewById(R.id.viewpager_indicator);

        List<Integer> productImages=new ArrayList<>();
        productImages.add(R.drawable.service);
        productImages.add(R.drawable.washandfold);
        productImages.add(R.drawable.details_relating);

        ServiceImageAdapter serviceImageAdapter=new ServiceImageAdapter(productImages);
        productImageViewPager.setAdapter(serviceImageAdapter);


        viewpagerIndicator.setupWithViewPager(productImageViewPager,true);






        
    }
}