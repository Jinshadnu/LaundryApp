package com.example.laundryapp.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.ActivityCartBinding;
import com.example.laundryapp.fragments.adapter.CartAdapter;
import com.example.laundryapp.fragments.pojo.Cart;
import com.example.laundryapp.fragments.viewmodel.CartViewModel;

import java.util.List;

public class CartActivity extends AppCompatActivity {
public ActivityCartBinding cartBinding;
public CartViewModel cartViewModel;
public CartAdapter cartAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        cartBinding= DataBindingUtil.setContentView(this,R.layout.activity_cart);

        cartViewModel = ViewModelProviders.of(this).get(CartViewModel.class);

        getcartItems();


    }

    private void getcartItems() {
        cartViewModel.getcartItems().observe((LifecycleOwner) this, new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> cartList) {
                cartAdapter=new CartAdapter(CartActivity.this,cartList);
                cartBinding.recyclerCart.setAdapter(cartAdapter);
            }
        });
    }
}