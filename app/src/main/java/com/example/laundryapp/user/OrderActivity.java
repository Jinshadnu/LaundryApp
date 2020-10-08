package com.example.laundryapp.user;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.ActivityOrderBinding;
import com.example.laundryapp.fragments.adapter.CartAdapter;
import com.example.laundryapp.fragments.adapter.CategoriesAdapater;
import com.example.laundryapp.fragments.adapter.ItemAdapter;
import com.example.laundryapp.fragments.adapter.OrderAdapter;
import com.example.laundryapp.fragments.pojo.Cart;
import com.example.laundryapp.fragments.pojo.Categories;
import com.example.laundryapp.fragments.pojo.Items;
import com.example.laundryapp.fragments.pojo.Orders;
import com.example.laundryapp.fragments.viewmodel.CategoriesViewModel;
import com.example.laundryapp.fragments.viewmodel.ItemsViewModel;
import com.example.laundryapp.fragments.viewmodel.OrderViewModel;
import com.example.laundryapp.user.interfaces.AddCartCallBack;
import com.example.laundryapp.utilities.GridSpacingItemDecoration;

import java.lang.invoke.MethodHandles;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class OrderActivity extends AppCompatActivity implements AddCartCallBack {
public ItemsViewModel itemsViewModel;
public static ActivityOrderBinding orderBinding;
public CategoriesAdapater categoriesAdapater;
public ItemAdapter itemAdapter;
public CategoriesViewModel categoriesViewModel;
    private static int cart_count=0;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        orderBinding= DataBindingUtil.setContentView(this,R.layout.activity_order);

        orderBinding.layoutBase.textTitle.setText("Items");

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



        categoriesViewModel=ViewModelProviders.of(this).get(CategoriesViewModel.class);


        itemsViewModel= ViewModelProviders.of(this).get(ItemsViewModel.class);

        fetchCategories();

        fetchItems();

        runAnimationAgain();
    }

    private void fetchItems() {
        itemsViewModel.fetchItems().observe((LifecycleOwner) this, new Observer<List<Items>>() {
            @Override
            public void onChanged(List<Items> items) {
                itemAdapter=new ItemAdapter(OrderActivity.this,items);
                orderBinding.recyclerProducts.setAdapter(itemAdapter);

                orderBinding.searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query)  {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        itemAdapter.getFilter().filter(newText);
                        return false;
                    }
                });
            }
        });
    }

    private void fetchCategories() {
        categoriesViewModel.getCategories().observe((LifecycleOwner) this, new Observer<List<Categories>>() {
            @Override
            public void onChanged(List<Categories> categoriesList) {
                categoriesAdapater=new CategoriesAdapater(OrderActivity.this,categoriesList);
                orderBinding.recyclerCategories.setAdapter(categoriesAdapater);
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

//public static void getTotal(int total){
//    orderBinding.textPrice.setText(String.valueOf(total));
//}


}