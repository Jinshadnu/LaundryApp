package com.example.laundryapp.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.ActivityHistoryBinding;
import com.example.laundryapp.fragments.adapter.OrderAdapter;
import com.example.laundryapp.login.LoginActivity;
import com.example.laundryapp.user.viewModel.OrderViewModel;
import com.example.laundryapp.utilities.BaseActivity;
import com.example.laundryapp.utilities.Constants;
import com.example.laundryapp.utilities.NetworkUtilities;

public class HistoryActivity extends BaseActivity {
public ActivityHistoryBinding historyBinding;
    public OrderViewModel orderViewModel;
    public OrderAdapter orderAdapter;
    public ProgressDialog progressDialog;
    public String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        historyBinding= DataBindingUtil.setContentView(this,R.layout.activity_history);

        historyBinding.layoutBase.textTitle.setText("My Orders");

        historyBinding.layoutBase.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);

        historyBinding.layoutBase.toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });


        SharedPreferences sharedPreferences=getSharedPreferences(Constants.MyPREFERENCES,MODE_PRIVATE);
        user_id=sharedPreferences.getString(Constants.USER_ID,null);
        historyBinding.recyclerOrders.setLayoutManager(new GridLayoutManager(this,1));
        historyBinding.recyclerOrders.setHasFixedSize(true);

        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);

        fetchOrders();
        runAnimationAgain();

    }

    private void fetchOrders() {
        if (NetworkUtilities.getNetworkInstance(this).isConnectedToInternet()){
            progressDialog = new ProgressDialog(HistoryActivity.this);
            progressDialog.setMessage("Loading.....");
            progressDialog.show();

            orderViewModel.getOrders(user_id).observe(this,orderResponse -> {
                progressDialog.dismiss();

                if (orderResponse != null && orderResponse.getStatus().equals(Constants.SERVER_RESPONSE_SUCCESS)){
                    orderAdapter=new OrderAdapter(this,orderResponse.getOrders());
                    historyBinding.recyclerOrders.setAdapter(orderAdapter);
                }

            });
        }
        else {
            showSnackBar(this,"No internet connection");
        }

    }

    private void runAnimationAgain() {


        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(this, R.anim.gridlayout_animation_from_bottom);

        historyBinding.recyclerOrders.setLayoutAnimation(controller);
//        itemAdapter.notifyDataSetChanged();
        historyBinding.recyclerOrders.scheduleLayoutAnimation();

    }


}