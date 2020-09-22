package com.example.laundryapp.user;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.ActivityOrderBinding;
import com.example.laundryapp.fragments.adapter.ItemAdapter;
import com.example.laundryapp.fragments.adapter.OrderAdapter;
import com.example.laundryapp.fragments.pojo.Items;
import com.example.laundryapp.fragments.pojo.Orders;
import com.example.laundryapp.fragments.viewmodel.ItemsViewModel;
import com.example.laundryapp.fragments.viewmodel.OrderViewModel;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class OrderActivity extends AppCompatActivity {
public ItemsViewModel itemsViewModel;
public ActivityOrderBinding orderBinding;
public ItemAdapter itemAdapter;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        orderBinding= DataBindingUtil.setContentView(this,R.layout.activity_order);

        orderBinding.layoutBase.toolbar.setTitle("Place Order");

        orderBinding.recyclerProducts.setLayoutManager(new GridLayoutManager(this,2));
        orderBinding.recyclerProducts.setHasFixedSize(true);

        orderBinding.buttonOrder.setOnClickListener(v -> {
            startActivity(new Intent(OrderActivity.this,AddressActivity.class));
        });

        orderBinding.buttonMens.setOnClickListener(v -> {
            orderBinding.buttonWomens.setBackground(requireNonNull(this)
                    .getDrawable(R.drawable.clothes_background));
            orderBinding.buttonMens.setBackground(requireNonNull(this)
                    .getDrawable(R.drawable.capsule_background));
            orderBinding.buttonKids.setBackground(requireNonNull(this)
                    .getDrawable(R.drawable.clothes_background));
            orderBinding.buttonOther.setBackground(requireNonNull(this)
                    .getDrawable(R.drawable.clothes_background));
            orderBinding.buttonWomens.setTextColor(getResources().getColor(R.color.colorblack));
            orderBinding.buttonMens.setTextColor(getResources().getColor(R.color.colorwhite));
            orderBinding.buttonKids.setTextColor(getResources().getColor(R.color.colorblack));
            orderBinding.buttonOther.setTextColor(getResources().getColor(R.color.colorblack));

        });


        orderBinding.buttonWomens.setOnClickListener(v -> {
            orderBinding.buttonWomens.setBackground(requireNonNull(this)
                    .getDrawable(R.drawable.capsule_background));
            orderBinding.buttonMens.setBackground(requireNonNull(this)
                    .getDrawable(R.drawable.clothes_background));
            orderBinding.buttonKids.setBackground(requireNonNull(this)
                    .getDrawable(R.drawable.clothes_background));
            orderBinding.buttonOther.setBackground(requireNonNull(this)
                    .getDrawable(R.drawable.clothes_background));
            orderBinding.buttonWomens.setTextColor(getResources().getColor(R.color.colorwhite));
            orderBinding.buttonMens.setTextColor(getResources().getColor(R.color.colorblack));
            orderBinding.buttonKids.setTextColor(getResources().getColor(R.color.colorblack));
            orderBinding.buttonOther.setTextColor(getResources().getColor(R.color.colorblack));

        });

        orderBinding.buttonKids.setOnClickListener(v -> {
            orderBinding.buttonWomens.setBackground(requireNonNull(this)
                    .getDrawable(R.drawable.clothes_background));
            orderBinding.buttonMens.setBackground(requireNonNull(this)
                    .getDrawable(R.drawable.clothes_background));
            orderBinding.buttonKids.setBackground(requireNonNull(this)
                    .getDrawable(R.drawable.capsule_background));
            orderBinding.buttonOther.setBackground(requireNonNull(this)
                    .getDrawable(R.drawable.clothes_background));
            orderBinding.buttonKids.setTextColor(getResources().getColor(R.color.colorwhite));
            orderBinding.buttonWomens.setTextColor(getResources().getColor(R.color.colorblack));
            orderBinding.buttonMens.setTextColor(getResources().getColor(R.color.colorblack));
            orderBinding.buttonOther.setTextColor(getResources().getColor(R.color.colorblack));

        });

        orderBinding.buttonOther.setOnClickListener(v -> {
            orderBinding.buttonWomens.setBackground(requireNonNull(this)
                    .getDrawable(R.drawable.clothes_background));
            orderBinding.buttonMens.setBackground(requireNonNull(this)
                    .getDrawable(R.drawable.clothes_background));
            orderBinding.buttonKids.setBackground(requireNonNull(this)
                    .getDrawable(R.drawable.clothes_background));
            orderBinding.buttonOther.setBackground(requireNonNull(this)
                    .getDrawable(R.drawable.capsule_background));
            orderBinding.buttonOther.setTextColor(getResources().getColor(R.color.colorwhite));
            orderBinding.buttonMens.setTextColor(getResources().getColor(R.color.colorblack));
            orderBinding.buttonKids.setTextColor(getResources().getColor(R.color.colorblack));
            orderBinding.buttonWomens.setTextColor(getResources().getColor(R.color.colorblack));

        });





        itemsViewModel= ViewModelProviders.of(this).get(ItemsViewModel.class);

        fetchItems();
    }

    private void fetchItems() {
        itemsViewModel.fetchItems().observe((LifecycleOwner) this, new Observer<List<Items>>() {
            @Override
            public void onChanged(List<Items> items) {
                itemAdapter=new ItemAdapter(OrderActivity.this,items);
                orderBinding.recyclerProducts.setAdapter(itemAdapter);
            }
        });
    }
}