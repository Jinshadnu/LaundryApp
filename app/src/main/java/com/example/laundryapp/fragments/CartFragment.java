package com.example.laundryapp.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.FragmentCartBinding;
import com.example.laundryapp.fragments.adapter.CartAdapter;
import com.example.laundryapp.fragments.pojo.Cart;
import com.example.laundryapp.fragments.viewmodel.CartViewModel;
import com.example.laundryapp.user.PriceDetailsActivity;
import com.example.laundryapp.user.response.CartResponse;
import com.example.laundryapp.user.viewModel.AddCartViewModel;
import com.example.laundryapp.utilities.BaseActivity;
import com.example.laundryapp.utilities.Constants;
import com.example.laundryapp.utilities.RecyclerItemTouchHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener, CartAdapter.setOnActionListener {
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
    public String item_id,quantity,price;
    public String count;
    public int totalvalue;

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


//      baseActivity = (BaseActivity) getActivity();

        cartList=new ArrayList<>();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.MyPREFERENCES, Context.MODE_PRIVATE);
        user_id=sharedPreferences.getString(Constants.USER_ID,null);

        cartBinding.layoutBase.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        cartBinding.layoutBase.toolbar.setNavigationOnClickListener(v -> {
            getActivity().onBackPressed();
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


        cartBinding.orederLayout.buttonOrder.setOnClickListener(v -> {
            Intent intent=new Intent(getActivity(),PriceDetailsActivity.class);
            count=String.valueOf(cartAdapter.cartList.size());
            String totalPrice=cartBinding.orederLayout.total.getText().toString();
            intent.putExtra("qauntity",count);
            intent.putExtra("price",totalPrice);
            startActivity(intent);
        });


        //calculateTotal();

        //grandTotal(cartList);


        return cartBinding.getRoot();
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
                cartAdapter.setActionListener(this);
               // count=String.valueOf(cartAdapter.cartList.size());
               grandTotal();
            }
            if (cartAdapter.getItemCount() == 0){
                cartBinding.textNodata.setVisibility(View.VISIBLE);
                cartBinding.recyclerCart.setVisibility(View.GONE);
                cartBinding.orederLayout.layoutPrice.setVisibility(View.GONE);
            }
        });
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
                 //baseActivity.openSuccessDialog(comonResponse.getMessage());
                    Toast.makeText(getActivity(),comonResponse.getMessage(),Toast.LENGTH_LONG).show();
                }
            });


           fetchCart();

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


    @Override
    public void onActionPerformed(String item_id, String quantity,String price) {
        this.item_id=item_id;
        this.quantity=quantity;
        this.price=price;
        updateCartItem();


        //fetchCart();
        //cartBinding.orederLayout.textTotal.setVisibility(View.GONE);


    }

    public void updateCartItem(){
        addCartViewModel.updateCartItem(item_id,user_id,quantity,price).observe(getActivity(),updateResponse -> {
            if (updateResponse != null && updateResponse.getStatus().equals(Constants.SERVER_RESPONSE_SUCCESS)){
              String cart_total=String.valueOf(updateResponse.getOrder_total());
              cartBinding.orederLayout.total.setText(cart_total);
            }
        });
    }


}