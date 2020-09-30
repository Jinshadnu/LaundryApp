package com.example.laundryapp.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.ActivityCartBinding;
import com.example.laundryapp.fragments.adapter.CartAdapter;
import com.example.laundryapp.fragments.pojo.Cart;
import com.example.laundryapp.fragments.viewmodel.CartViewModel;

import java.util.List;

public class CartActivity extends AppCompatActivity {
public static ActivityCartBinding cartBinding;
public CartViewModel cartViewModel;
public CartAdapter cartAdapter;
    public int totalAmount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        cartBinding= DataBindingUtil.setContentView(this,R.layout.activity_cart);

        cartBinding.layoutBase.textTitle.setText("My cart");

        cartBinding.layoutBase.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);

        cartBinding.layoutBase.toolbar.setNavigationOnClickListener(v -> {
            this.onBackPressed();
        });



        cartViewModel = ViewModelProviders.of(this).get(CartViewModel.class);

        cartBinding.orederLayout.buttonOrder.setOnClickListener(v -> {
            startActivity(new Intent(this, PriceDetailsActivity.class));
        });

        cartBinding.recyclerCart.setLayoutManager(new GridLayoutManager(this,1));
        cartBinding.recyclerCart.setHasFixedSize(true);



        getcartItems();

        runAnimationAgain();

      // calculateTotal();




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

    private void runAnimationAgain() {


        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(this, R.anim.gridlayout_animation_from_bottom);

        cartBinding.recyclerCart.setLayoutAnimation(controller);
//        itemAdapter.notifyDataSetChanged();
        cartBinding.recyclerCart.scheduleLayoutAnimation();

    }

    public static void getTotal(int total){
        cartBinding.orederLayout.total.setText(String.valueOf(total));
    }

    private void calculateTotal() {
        for (int i = 0; i < cartAdapter.cartList.size(); i++) {

            int quantity=cartAdapter.cartList.get(i).getQuantity();
            int price=cartAdapter.cartList.get(i).getPrice();
            price=price*quantity;
            totalAmount=totalAmount + price;



        }

        cartBinding.orederLayout.total.setText(String.valueOf(totalAmount));


    }


}