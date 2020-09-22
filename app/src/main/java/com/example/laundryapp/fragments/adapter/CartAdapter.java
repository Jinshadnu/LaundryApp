package com.example.laundryapp.fragments.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.LayoutCartBinding;
import com.example.laundryapp.databinding.LayoutItemsBinding;
import com.example.laundryapp.fragments.pojo.Cart;
import com.example.laundryapp.fragments.pojo.Items;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import static android.view.LayoutInflater.from;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewModel> {
    public Context context;
    public List<Cart> cartList;

    public CartAdapter(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }


    @NonNull
    @Override
    public CartViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutCartBinding cartBinding= DataBindingUtil.inflate(from(context), R.layout.layout_cart,parent,false);
        return new CartViewModel(cartBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewModel holder, int position) {
        Cart cart=cartList.get(position);
        holder.cartBinding.setCarts(cart);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class CartViewModel extends RecyclerView.ViewHolder {
        public LayoutCartBinding cartBinding;

        public CartViewModel(@NonNull LayoutCartBinding layoutCartBinding) {
            super(layoutCartBinding.getRoot());

            this.cartBinding=layoutCartBinding;
        }
    }
}
