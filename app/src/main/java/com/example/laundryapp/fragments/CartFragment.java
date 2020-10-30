package com.example.laundryapp.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.FragmentCartBinding;
import com.example.laundryapp.fragments.adapter.CartAdapter;
import com.example.laundryapp.fragments.adapter.OrderAdapter;
import com.example.laundryapp.fragments.pojo.Cart;
import com.example.laundryapp.fragments.pojo.Orders;
import com.example.laundryapp.fragments.viewmodel.CartViewModel;
import com.example.laundryapp.fragments.viewmodel.OrderViewModel;
import com.example.laundryapp.user.AddressActivity;
import com.example.laundryapp.user.PriceDetailsActivity;
import com.example.laundryapp.user.response.CartResponse;
import com.example.laundryapp.user.viewModel.AddCartViewModel;
import com.example.laundryapp.utilities.BaseActivity;
import com.example.laundryapp.utilities.Constants;
import com.example.laundryapp.utilities.RecyclerItemTouchHelper;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    public CartViewModel cartViewModel;
    public AddCartViewModel addCartViewModel;
    public CartAdapter cartAdapter;
    public static FragmentCartBinding cartBinding;
    public static int total = 0;
    public Context context;
    public int totalAmount = 0;
    private BaseActivity baseActivity;
    public List<CartResponse.Carts> cartList;
    public String user_id;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        cartViewModel = ViewModelProviders.of((FragmentActivity) this.getActivity()).get(CartViewModel.class);
        addCartViewModel=ViewModelProviders.of((FragmentActivity) this.getActivity()).get(AddCartViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        cartBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false);

        cartBinding.layoutBase.textTitle.setText("My Cart");





        cartList=new ArrayList<>();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.MyPREFERENCES, Context.MODE_PRIVATE);
        user_id=sharedPreferences.getString(Constants.USER_ID,null);

        cartBinding.layoutBase.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        cartBinding.layoutBase.toolbar.setNavigationOnClickListener(v -> {
            getActivity().onBackPressed();
        });

        cartBinding.orederLayout.buttonOrder.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), PriceDetailsActivity.class));
        });

        // cartBinding.orederLayout.textTotal.

        cartBinding.recyclerCart.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        cartBinding.recyclerCart.setHasFixedSize(true);



        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(cartBinding.recyclerCart);

        List<Cart> shoppingCart = new ArrayList<>();
        setPayableAmount(shoppingCart);

        runAnimationAgain();

        fetchCart();

        calculateTotal();

        //grandTotal(cartList);


        return cartBinding.getRoot();
    }

    public void calculateTotal() {

        for (int i = 0; i < cartList.size(); i++) {

            int quantity = Integer.parseInt(cartAdapter.cartList.get(i).getQuantity());
            int price = Integer.parseInt(cartAdapter.cartList.get(i).getPrice());
            price = price * quantity;
           totalAmount = totalAmount + price;
        }

        cartBinding.orederLayout.total.setText(String.valueOf(totalAmount));


    }
      private int grandTotal(List<CartResponse.Carts> items){

        int totalPrice = 0;
        for(int i = 0 ; i < items.size(); i++) {
            totalPrice += Integer.parseInt(items.get(i).getPrice());
        }

        cartBinding.orederLayout.total.setText(String.valueOf(totalPrice));

        return totalPrice;
    }

//    private void getcartItems() {
//        cartViewModel.getcartItems().observe((LifecycleOwner) this.getActivity(), new Observer<List<Cart>>() {
//            @Override
//            public void onChanged(List<Cart> cartList) {
//                cartAdapter = new CartAdapter(getActivity(), cartList);
//                cartBinding.recyclerCart.setAdapter(cartAdapter);
//
//                cartBinding.searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                    @Override
//                    public boolean onQueryTextSubmit(String query) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onQueryTextChange(String newText) {
//                        cartAdapter.getFilter().filter(newText);
//                        return false;
//                    }
//                });
//            }
//        });
//    }

    public void fetchCart(){
        addCartViewModel.getCartItems(user_id).observe(getActivity(),cartResponse -> {
            if (cartResponse != null && cartResponse.getStatus().equals(Constants.SERVER_RESPONSE_SUCCESS)){
                cartAdapter=new CartAdapter(getActivity(),cartResponse.getCart());
                cartBinding.recyclerCart.setAdapter(cartAdapter);
            }
        });
    }



    private void runAnimationAgain() {


        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.gridlayout_animation_from_bottom);

        cartBinding.recyclerCart.setLayoutAnimation(controller);
//        itemAdapter.notifyDataSetChanged();
        cartBinding.recyclerCart.scheduleLayoutAnimation();

    }

    public static void getTotal(int total) {
        cartBinding.orederLayout.total.setText(String.valueOf(total));
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
                 baseActivity.openSuccessDialog(comonResponse.getMessage());
                }
            });


            calculateTotal();

            // showing snack bar with Undo option
//            Snackbar snackbar = Snackbar
//                    .make(coordinatorLayout, name + " removed from cart!", Snackbar.LENGTH_LONG);
//            snackbar.setAction("UNDO", new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    // undo is selected, restore the deleted item
//                    mAdapter.restoreItem(deletedItem, deletedIndex);
//                }
//            });
//            snackbar.setActionTextColor(Color.YELLOW);
//            snackbar.show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void setPayableAmount(List<Cart> shoppingCart) {
        Double totalAmount = 0.0;
        for (int i = 0; i < shoppingCart.size(); i++) {
            int itemQuantity = shoppingCart.get(i).getQuantity();
            int price = shoppingCart.get(i).getPrice();
            price = price * itemQuantity;
            totalAmount = totalAmount + price;
        }
    }



}