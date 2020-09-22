package com.example.laundryapp.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.ActivityHistoryBinding;
import com.example.laundryapp.fragments.adapter.OrderAdapter;
import com.example.laundryapp.fragments.pojo.Orders;
import com.example.laundryapp.fragments.viewmodel.OrderViewModel;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {
public ActivityHistoryBinding historyBinding;
    public OrderViewModel orderViewModel;
    public OrderAdapter orderAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        historyBinding= DataBindingUtil.setContentView(this,R.layout.activity_history);

        historyBinding.layoutBase.toolbar.setTitle("My Orders");

        historyBinding.recyclerOrders.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        historyBinding.recyclerOrders.setHasFixedSize(true);

        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);

        fetchOrders();

    }

    private void fetchOrders() {
        orderViewModel.fetchOrders().observe((LifecycleOwner) this, new Observer<List<Orders>>() {
            @Override
            public void onChanged(List<Orders> orders) {
                orderAdapter=new OrderAdapter(HistoryActivity.this,orders);
                historyBinding.recyclerOrders.setAdapter(orderAdapter);
            }
        });
    }


}