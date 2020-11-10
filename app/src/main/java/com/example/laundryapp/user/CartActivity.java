package com.example.laundryapp.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.ActivityCartBinding;
import com.example.laundryapp.fragments.adapter.CartAdapter;
import com.example.laundryapp.fragments.pojo.Cart;
import com.example.laundryapp.fragments.viewmodel.CartViewModel;
import com.example.laundryapp.user.response.CartResponse;
import com.example.laundryapp.user.viewModel.AddCartViewModel;
import com.example.laundryapp.utilities.BaseActivity;
import com.example.laundryapp.utilities.Constants;
import com.example.laundryapp.utilities.RecyclerItemTouchHelper;

import java.util.List;

public class CartActivity extends BaseActivity implements  RecyclerItemTouchHelper.RecyclerItemTouchHelperListener,CartAdapter.setOnActionListener {
public static ActivityCartBinding cartBinding;
public CartViewModel cartViewModel;
public AddCartViewModel addCartViewModel;
public CartAdapter cartAdapter;
public String item_id,quantity,price;
    public String count;
    public String user_id;
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

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.MyPREFERENCES, Context.MODE_PRIVATE);
        user_id=sharedPreferences.getString(Constants.USER_ID,null);


        addCartViewModel=ViewModelProviders.of((FragmentActivity) this).get(AddCartViewModel.class);

        cartBinding.orederLayout.buttonOrder.setOnClickListener(v -> {
            Intent intent=new Intent(CartActivity.this,PriceDetailsActivity.class);
            count=String.valueOf(cartAdapter.cartList.size());
            String totalPrice=cartBinding.orederLayout.total.getText().toString();
            intent.putExtra("qauntity",count);
            intent.putExtra("price",totalPrice);
            startActivity(intent);
        });

        cartBinding.recyclerCart.setLayoutManager(new GridLayoutManager(this,1));
        cartBinding.recyclerCart.setHasFixedSize(true);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(cartBinding.recyclerCart);



        //getcartItems();

        runAnimationAgain();

        fetchCart();

      // calculateTotal();




    }

//    private void getcartItems() {
//        cartViewModel.getcartItems().observe((LifecycleOwner) this, new Observer<List<Cart>>() {
//            @Override
//            public void onChanged(List<Cart> cartList) {
//                cartAdapter=new CartAdapter(CartActivity.this,cartList);
//                cartBinding.recyclerCart.setAdapter(cartAdapter);
//            }
//        });
//    }

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

    private int grandTotal(){
        //cartList=new ArrayList<>();
        int totalPrice = 0;
        for(int i = 0 ; i < cartAdapter.cartList.size(); i++) {
            totalPrice += Double.parseDouble(cartAdapter.cartList.get(i).getPrice());
        }

        cartBinding.orederLayout.total.setText(String.valueOf(totalPrice));

        return totalPrice;
    }


    public void calculateTotal() {

        for (int i = 0; i < cartAdapter.cartList.size(); i++) {

            int quantity = Integer.parseInt(cartAdapter.cartList.get(i).getQuantity());
            int price = Integer.parseInt(cartAdapter.cartList.get(i).getPrice());
            price = price * quantity;
            totalAmount = totalAmount + price;
        }

        cartBinding.orederLayout.total.setText(String.valueOf(totalAmount));


    }
public void fetchCart(){
    addCartViewModel.getCartItems(user_id).observe(this,cartResponse -> {
        if (cartResponse != null && cartResponse.getStatus().equals(Constants.SERVER_RESPONSE_SUCCESS)){
            cartAdapter=new CartAdapter(this,cartResponse.getCart());
            cartBinding.recyclerCart.setAdapter(cartAdapter);
            cartAdapter.setActionListener(this);
            grandTotal();
        }

        if (cartAdapter.getItemCount() == 0){
            cartBinding.textNodata.setVisibility(View.VISIBLE);
            cartBinding.recyclerCart.setVisibility(View.GONE);
            cartBinding.orederLayout.layoutPrice.setVisibility(View.GONE);
        }
    });
}

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof CartAdapter.CartViewModel) {
            // get the removed item name to display it in snack bar
            //  String name = cartList.get(viewHolder.getAdapterPosition()).getItem_name();
            position= viewHolder.getAdapterPosition();

            String item_id=cartAdapter.cartList.get(position).getItem_id();

            cartAdapter.removeItem(viewHolder.getAdapterPosition());
            // backup of removed item for undo purpose
//            final Cart deletedItem = cartList.get(viewHolder.getAdapterPosition());
//            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            //cartAdapter.removeItem(viewHolder.getAdapterPosition());

            addCartViewModel.deletecartItem(user_id,item_id).observe(this,comonResponse -> {
                if (comonResponse != null && comonResponse.getStatus().equals(Constants.SERVER_RESPONSE_SUCCESS)){
                    openSuccessDialog(comonResponse.getMessage());

                    fetchCart();
                }
            });

           // fetchCart();

            //calculateTotal();


        }
    }

    @Override
    public void onActionPerformed(String item_id, String quantity,String price) {
        this.item_id=item_id;
        this.quantity=quantity;
        this.price=price;
        updateCartItem();



    }
    public void updateCartItem(){
        addCartViewModel.updateCartItem(item_id,user_id,quantity,price).observe(this,updateResponse -> {
            if (updateResponse != null && updateResponse.getStatus().equals(Constants.SERVER_RESPONSE_SUCCESS)) {
             // fetchCart();
                String cart_total=String.valueOf(updateResponse.getOrder_total());
                cartBinding.orederLayout.total.setText(cart_total);
            }
        });
    }




//    @Override
//    public void updatePayableAmount(List<CartResponse.Carts> shoppingCart) {
//        setPayableAmount(shoppingCart);
//    }
//
//    private void setPayableAmount(List<CartResponse.Carts> shoppingCart) {
//        Double totalAmount = 0.0;
//        for (int i = 0; i < shoppingCart.size(); i++) {
//            int itemQuantity = Integer.parseInt(shoppingCart.get(i).getQuantity());
//            Double price = Double.valueOf(shoppingCart.get(i).getPrice());
//            //price = price * itemQuantity;
//            totalAmount = totalAmount + price;
//        }
//
//        cartBinding.orederLayout.total.setText(String.valueOf(totalAmount));
//
//
//    }
    }