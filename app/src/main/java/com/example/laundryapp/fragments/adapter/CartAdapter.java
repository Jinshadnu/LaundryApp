package com.example.laundryapp.fragments.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.LayoutCartBinding;
import com.example.laundryapp.databinding.LayoutItemsBinding;
import com.example.laundryapp.fragments.pojo.Cart;
import com.example.laundryapp.fragments.pojo.Items;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import static android.view.LayoutInflater.from;
import static com.example.laundryapp.fragments.CartFragment.getTotal;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewModel> implements Filterable {
    public Context context;
    public List<Cart> cartList;
    public List<Cart> cartListFiltered;
    public static List <Cart> selecteditems;
    public static int total=0;
    public int price,quantity,totals;
    public ValueFilter valueFilter;
    public static LayoutCartBinding cartBinding;



    public CartAdapter(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
        this.cartListFiltered=cartList;
    }


    @NonNull
    @Override
    public CartViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        cartBinding= DataBindingUtil.inflate(from(context), R.layout.layout_cart,parent,false);
        return new CartViewModel(cartBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewModel holder, int position) {
        Cart cart=cartList.get(position);
        holder.cartBinding.setCarts(cart);
        int i=0;
        total=0;

        holder.cartBinding.elegantCount.setOnValueChangeListener((view, oldValue, newValue) -> {
            //getTotal();
            String quantity=holder.cartBinding.elegantCount.getNumber();
            int count=Integer.parseInt(quantity);
            price=cartList.get(position).getPrice();
            totals=totals+price*count;
            holder.cartBinding.textPrice.setText(String.valueOf(calculatePrice(price,count)));

            //getTotal(total);
        });
    }

    private int  calculatePrice(int priceValue, int quantity) {
        return priceValue * quantity;
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public void removeItem(int position) {
        cartList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Cart item, int position) {
        cartList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    public class ValueFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                List<Cart> filterList = new ArrayList<>();
                for (int i = 0; i < cartListFiltered.size(); i++) {
                    if ((cartListFiltered.get(i).getItem_name().toUpperCase()).contains(constraint.toString().toUpperCase())) {
                        filterList.add(cartListFiltered.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = cartListFiltered.size();
                results.values = cartListFiltered;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            cartList = (List<Cart>) results.values;
            notifyDataSetChanged();
        }
    }

    public class CartViewModel extends RecyclerView.ViewHolder {
        public LayoutCartBinding cartBinding;

        public CartViewModel(@NonNull LayoutCartBinding layoutCartBinding) {
            super(layoutCartBinding.getRoot());

            this.cartBinding=layoutCartBinding;
        }
    }


}
