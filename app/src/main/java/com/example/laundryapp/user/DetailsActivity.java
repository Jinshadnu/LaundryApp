package com.example.laundryapp.user;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
    public TextView textView,textView_description,textView_serviceName;
    public Button button_order;
    public String description,pos,service_name;
    public int position;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        button_order=(Button)findViewById(R.id.button_order);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        textView=(TextView) findViewById(R.id.text_title);
        textView_description=(TextView)findViewById(R.id.textView_description);
        textView_serviceName=(TextView)findViewById(R.id.textview_service_name);
        description=getIntent().getStringExtra("description");
        service_name=getIntent().getStringExtra("service_name");
        textView_description.setText(description);
        textView_serviceName.setText(service_name);
        pos=getIntent().getStringExtra("position");



        textView.setText("Service Details");

        ImageView backButton = findViewById(R.id.backButton);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

//       toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
//        toolbar.setNavigationOnClickListener(v -> {
//            this.onBackPressed();
//        });

//        toolbar.setTitle("Service Details");

        setSupportActionBar(toolbar);

        //toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);






        button_order.setOnClickListener(v -> {
            Intent intent=new Intent(DetailsActivity.this,OrderActivity.class);
            intent.putExtra("position",pos);
            intent.putExtra("service_name",service_name);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
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