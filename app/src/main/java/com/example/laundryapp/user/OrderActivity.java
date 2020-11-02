package com.example.laundryapp.user;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.ActivityOrderBinding;
import com.example.laundryapp.fragments.adapter.CategoriesAdapater;
import com.example.laundryapp.fragments.adapter.ItemAdapter;
import com.example.laundryapp.fragments.viewmodel.ItemsViewModel;
import com.example.laundryapp.fragments.viewmodel.ServiceViewModel;
import com.example.laundryapp.user.interfaces.AddCartCallBack;
import com.example.laundryapp.user.viewModel.AddCartViewModel;
import com.example.laundryapp.utilities.BaseActivity;
import com.example.laundryapp.utilities.Constants;
import com.example.laundryapp.utilities.GridSpacingItemDecoration;

import static java.util.Objects.requireNonNull;

public class OrderActivity extends BaseActivity implements AddCartCallBack,CategoriesAdapater.ItemClickListener {
public ItemsViewModel itemsViewModel;
public static ActivityOrderBinding orderBinding;
public AddCartViewModel cartViewModel;
public CategoriesAdapater categoriesAdapater;
public ItemAdapter itemAdapter;
public ServiceViewModel serviceViewModel;
public int pos;
    private static int cart_count=0;
    public int position;
    public String user_id,service_name;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        orderBinding= DataBindingUtil.setContentView(this,R.layout.activity_order);

        orderBinding.layoutBase.textTitle.setText("Items");

        pos=Integer.parseInt(getIntent().getStringExtra("position"));
        service_name=getIntent().getStringExtra("service_name");


        SharedPreferences sharedPreferences=getSharedPreferences(Constants.MyPREFERENCES,MODE_PRIVATE);
        user_id=sharedPreferences.getString(Constants.USER_ID,null);

        orderBinding.layoutBase.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);


        orderBinding.layoutBase.imageViewCart.setOnClickListener(v -> {
            startActivity(new Intent(OrderActivity.this,CartActivity.class));
        });

        orderBinding.layoutBase.toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });

        orderBinding.recyclerCategories.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));
        orderBinding.recyclerCategories.setHasFixedSize(true);


        orderBinding.recyclerProducts.setLayoutManager(new GridLayoutManager(this,2));
        orderBinding.recyclerProducts.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));

        orderBinding.recyclerProducts.setHasFixedSize(true);



        serviceViewModel=ViewModelProviders.of(this).get(ServiceViewModel.class);


        itemsViewModel= ViewModelProviders.of(this).get(ItemsViewModel.class);

        cartViewModel=ViewModelProviders.of(this).get(AddCartViewModel.class);


        fetchCategories();



        runAnimationAgain();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchItems(0);
    }

    private void fetchItems(int clickedPosition) {
        serviceViewModel.getServices().observe((LifecycleOwner) this, serviceResponse -> {
            if (serviceResponse != null && serviceResponse.getStatus().equals(Constants.SERVER_RESPONSE_SUCCESS)){
             itemAdapter=new ItemAdapter(this,serviceResponse.getServices().get(pos).getCategory().get(clickedPosition).getItems());
             orderBinding.recyclerProducts.setAdapter(itemAdapter);
            }

            });
        }






        private void runAnimationAgain() {


        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(this, R.anim.gridlayout_animation_from_bottom);

        orderBinding.recyclerProducts.setLayoutAnimation(controller);
//        itemAdapter.notifyDataSetChanged();
        orderBinding.recyclerProducts.scheduleLayoutAnimation();

    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void onAddProduct() {
        cart_count++;
        orderBinding.layoutBase.count.setVisibility(View.VISIBLE);
        orderBinding.layoutBase.count.setText(String.valueOf(cart_count));
    }

    @Override
    public void onRemoveProduct() {

    }

    @Override
    public void addToCart(String item_id,String quantity) {
        cartViewModel.addToCart(user_id,service_name,item_id,quantity).observe(this,comonResponse -> {
            if (comonResponse != null && comonResponse.getStatus().equals(Constants.SERVER_RESPONSE_SUCCESS)){
                showSnackBar(this,comonResponse.getMessage());
            }
        });
    }


    @Override
    public void onClick(View view, int position) {
     categoriesAdapater.setItemClickListener(this);
     this.position=position;
    }

//public static void getTotal(int total){
//    orderBinding.textPrice.setText(String.valueOf(total));
//}
private void fetchCategories() {
    serviceViewModel.getServices().observe((LifecycleOwner) this, serviceResponse ->  {
     if (serviceResponse != null && serviceResponse.getStatus().equals(Constants.SERVER_RESPONSE_SUCCESS)){
       categoriesAdapater=new CategoriesAdapater(this, serviceResponse.getServices().get(pos).getCategory());
       orderBinding.recyclerCategories.setAdapter(categoriesAdapater);
       fetchItems(0);
       categoriesAdapater.setItemClickListener((view, position1) -> {
        fetchItems(position1);
       });
     }
    });

}


}