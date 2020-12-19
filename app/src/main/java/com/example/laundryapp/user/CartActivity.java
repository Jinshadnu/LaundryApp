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
import android.widget.Toast;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.ActivityCartBinding;
import com.example.laundryapp.fragments.adapter.CartAdapter;
import com.example.laundryapp.fragments.pojo.Cart;
import com.example.laundryapp.fragments.viewmodel.CartViewModel;
import com.example.laundryapp.user.response.CartResponse;
import com.example.laundryapp.user.viewModel.AddCartViewModel;
import com.example.laundryapp.utilities.BaseActivity;
import com.example.laundryapp.utilities.Constants;
import com.example.laundryapp.utilities.NetworkUtilities;
import com.example.laundryapp.utilities.RecyclerItemTouchHelper;

import java.util.List;

public class CartActivity extends BaseActivity implements  RecyclerItemTouchHelper.RecyclerItemTouchHelperListener,CartAdapter.setOnActionListener,CartAdapter.onDeleteListener {
public static ActivityCartBinding cartBinding;
public CartViewModel cartViewModel;
public AddCartViewModel addCartViewModel;
public CartAdapter cartAdapter;
public String item_id,quantity,price;
public  int quant;
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

        cartBinding.buttonOrder.setOnClickListener(v -> {
            Intent intent=new Intent(CartActivity.this,PriceDetailsActivity.class);
            count=String.valueOf(cartAdapter.cartList.size());
            String totalPrice=cartBinding.total.getText().toString();
            double price=Double.parseDouble(cartBinding.total.getText().toString());
            intent.putExtra("qauntity",count);
            intent.putExtra("price",totalPrice);
            intent.putExtra("amount",price);
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
        cartBinding.total.setText(String.valueOf(total));
    }

    private double grandTotal(){
        //cartList=new ArrayList<>();
        double totalPrice = 0.0;

        for(int i = 0 ; i < cartAdapter.cartList.size(); i++) {
           // totalPrice += Double.parseDouble(cartAdapter.cartList.get(i).getPrice());
            quant=Integer.parseInt(cartAdapter.cartList.get(i).getQuantity());
            double price=Double.parseDouble(cartAdapter.cartList.get(i).getPrice());
            price=price * quant;
            totalPrice=totalPrice+ price;


        }

        cartBinding.total.setText(String.valueOf(totalPrice)+"0");

        return totalPrice;
    }


    public void calculateTotal() {

        for (int i = 0; i < cartAdapter.cartList.size(); i++) {

            int quantity = Integer.parseInt(cartAdapter.cartList.get(i).getQuantity());
            int price = Integer.parseInt(cartAdapter.cartList.get(i).getPrice());
            price = price * quantity;
            totalAmount = totalAmount + price;
        }

        cartBinding.total.setText(String.valueOf(totalAmount));


    }
public void fetchCart(){
    if (NetworkUtilities.getNetworkInstance(this).isConnectedToInternet()){
        addCartViewModel.getCartItems(user_id).observe(this,cartResponse -> {
            if (cartResponse != null && cartResponse.getStatus().equals(Constants.SERVER_RESPONSE_SUCCESS)){
                cartAdapter=new CartAdapter(this,cartResponse.getCart(),user_id);
                cartBinding.recyclerCart.setAdapter(cartAdapter);
                cartAdapter.setActionListener(this);
                cartAdapter.setDeleteListener(this);
                grandTotal();
            }

            if (cartAdapter.getItemCount() == 0){
                cartBinding.textNodata.setVisibility(View.VISIBLE);
                cartBinding.recyclerCart.setVisibility(View.GONE);
                cartBinding.layoutPrice.setVisibility(View.GONE);
            }
        });
    }
    else {
        cartBinding.buttonOrder.setVisibility(View.GONE);
        showErrorSnackBar(this,"No Internet Connection");
    }

}


    public void getCart(){
        addCartViewModel.getCartItems(user_id).observe(this,cartResponse -> {
            if (cartResponse != null && cartResponse.getStatus().equals(Constants.SERVER_RESPONSE_SUCCESS)){
                cartAdapter=new CartAdapter(this,cartResponse.getCart(),user_id);
                cartBinding.recyclerCart.setAdapter(cartAdapter);
                cartAdapter.setActionListener(this);
                //grandTotal();

            }

            if (cartAdapter.getItemCount() == 0){
                cartBinding.textNodata.setVisibility(View.VISIBLE);
                cartBinding.recyclerCart.setVisibility(View.GONE);
                cartBinding.layoutPrice.setVisibility(View.GONE);
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

            if (NetworkUtilities.getNetworkInstance(this).isConnectedToInternet()){
                addCartViewModel.deletecartItem(user_id,item_id).observe(this,comonResponse -> {
                    if (comonResponse != null && comonResponse.getStatus().equals(Constants.SERVER_RESPONSE_SUCCESS)){
                        openSuccessDialog(comonResponse.getMessage());

                        fetchCart();

                    }
                });
            }

            else {
                showErrorSnackBar(this,"No Internet Connection");
            }



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
        if (NetworkUtilities.getNetworkInstance(this).isConnectedToInternet()){
            addCartViewModel.updateCartItem(item_id,user_id,quantity,price).observe(this,updateResponse -> {
                if (updateResponse != null && updateResponse.getStatus().equals(Constants.SERVER_RESPONSE_SUCCESS)) {
                    // fetchCart();
                    String cart_total=String.valueOf(updateResponse.getOrder_total());
                    cartBinding.total.setText(cart_total+".00");
                }
            });
        }
        else {
            showErrorSnackBar(this,"No Internet Connection");
        }


    }

    @Override
    public void onDelete(String userId, String itemId) {
        if (NetworkUtilities.getNetworkInstance(this).isConnectedToInternet()){
            addCartViewModel.deletecartItem(userId,itemId).observe(this,comonResponse -> {
                if (comonResponse != null && comonResponse.getStatus().equals(Constants.SERVER_RESPONSE_SUCCESS)){
                    //baseActivity.openSuccessDialog(comonResponse.getMessage());
                    Toast.makeText(this,comonResponse.getMessage(),Toast.LENGTH_LONG).show();
                    fetchCart();
                }
            });

        }

        else {
            Toast.makeText(this,"No Internet connection",Toast.LENGTH_LONG).show();
        }
    }
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


    