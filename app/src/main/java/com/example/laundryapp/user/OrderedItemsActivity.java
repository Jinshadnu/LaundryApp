package com.example.laundryapp.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.ActivityOrderedItemsBinding;
import com.example.laundryapp.user.adapter.OrderedItemAdapter;
import com.example.laundryapp.user.viewModel.OrderViewModel;
import com.example.laundryapp.utilities.NetworkUtilities;

public class OrderedItemsActivity extends AppCompatActivity {
public ActivityOrderedItemsBinding orderedItemsBinding;
public OrderViewModel orderViewModel;
public String orderId;
public OrderedItemAdapter itemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        orderedItemsBinding= DataBindingUtil.setContentView(this,R.layout.activity_ordered_items);

        orderedItemsBinding.layoutBase.textTitle.setText("Ordered Items");

        orderedItemsBinding.layoutBase.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);

        orderedItemsBinding.layoutBase.toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });

        orderedItemsBinding.layoutBase.imageViewCart.setOnClickListener(v -> {
            startActivity(new Intent(OrderedItemsActivity.this,CartActivity.class));
        });

        orderId=getIntent().getStringExtra("orderId");

        orderViewModel= ViewModelProviders.of(this).get(OrderViewModel.class);
        orderedItemsBinding.recyclerOrders.setLayoutManager(new GridLayoutManager(this,1));
        orderedItemsBinding.recyclerOrders.setHasFixedSize(true);


        fetchItems();
    }

    public void fetchItems(){
        if (NetworkUtilities.getNetworkInstance(this).isConnectedToInternet()){
            orderViewModel.getItems(orderId).observe(this,orderedItemResponse -> {
                itemAdapter=new OrderedItemAdapter(this,orderedItemResponse.getItems());
                orderedItemsBinding.recyclerOrders.setAdapter(itemAdapter);
            });
        }
    }


}