package com.example.laundryapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

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

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {
    public CartViewModel cartViewModel;
    public CartAdapter cartAdapter;
    public static FragmentCartBinding cartBinding;
    public static int total=0;
    public Context context;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        cartBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false);

        cartBinding.layoutBase.toolbar.setTitle("My Cart");

        cartBinding.layoutBase.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        cartBinding.layoutBase.toolbar.setNavigationOnClickListener(v -> {
            getActivity().onBackPressed();
        });

        cartBinding.orederLayout.buttonOrder.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), AddressActivity.class));
        });

       // cartBinding.orederLayout.textTotal.

        cartBinding.recyclerCart.setLayoutManager(new GridLayoutManager(getActivity(),1));
        cartBinding.recyclerCart.setHasFixedSize(true);

        getcartItems();

        runAnimationAgain();


        //calculateTotal();



        return cartBinding.getRoot();
    }

//    private void calculateTotal() {
//        int i=0;
//        total=0;
//        while(i<CartAdapter.selecteditems.size()){
//            total= total + ( Integer.valueOf(CartAdapter.selecteditems.get(i).getPrice()) * Integer.valueOf(CartAdapter.selecteditems.get(i).getQuantity()) );
//            i++;
//        }
//        cartBinding.orederLayout.textTotal.setText(""+total);
//    }

    private void getcartItems() {
        cartViewModel.getcartItems().observe((LifecycleOwner) this.getActivity(), new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> cartList) {
                cartAdapter=new CartAdapter(getActivity(),cartList);
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

    public static void getTotal(int total){
      cartBinding.orederLayout.total.setText(String.valueOf(total));
    }






}