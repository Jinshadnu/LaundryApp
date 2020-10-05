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

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.ActivityOrderBinding;
import com.example.laundryapp.fragments.adapter.ItemAdapter;
import com.example.laundryapp.fragments.adapter.OrderAdapter;
import com.example.laundryapp.fragments.pojo.Items;
import com.example.laundryapp.fragments.pojo.Orders;
import com.example.laundryapp.fragments.viewmodel.ItemsViewModel;
import com.example.laundryapp.fragments.viewmodel.OrderViewModel;
import com.example.laundryapp.utilities.GridSpacingItemDecoration;

import java.lang.invoke.MethodHandles;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class OrderActivity extends AppCompatActivity {
public ItemsViewModel itemsViewModel;
public static ActivityOrderBinding orderBinding;
public ItemAdapter itemAdapter;
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

        orderBinding.recyclerProducts.setLayoutManager(new GridLayoutManager(this,2));
        orderBinding.recyclerProducts.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));

        orderBinding.recyclerProducts.setHasFixedSize(true);

//

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

//public static void getTotal(int total){
//    orderBinding.textPrice.setText(String.valueOf(total));
//}


}