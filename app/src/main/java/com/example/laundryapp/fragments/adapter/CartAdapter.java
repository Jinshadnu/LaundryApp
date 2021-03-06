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
import com.example.laundryapp.user.response.CartResponse;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import static android.view.LayoutInflater.from;
import static com.example.laundryapp.fragments.CartFragment.getTotal;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewModel> implements Filterable {
    public Context context;
    public List<CartResponse.Carts> cartList;
    public List<CartResponse.Carts> cartListFiltered;
    public static List <Cart> selecteditems;
    public static int total=0;
    public setOnActionListener listener;
    public onDeleteListener deleteListener;
    public int quantity;
    public ValueFilter valueFilter;
    public static LayoutCartBinding cartBinding;
    public String user_id;
    public Double price;
    public double totals=0.00;
    public double price_value;



    public CartAdapter(Context context, List<CartResponse.Carts> cartList,String user_id) {
        this.context = context;
        this.cartList = cartList;
        this.cartListFiltered=cartList;
        this.user_id=user_id;
    }


    @NonNull
    @Override
    public CartViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        cartBinding= DataBindingUtil.inflate(from(context), R.layout.layout_cart,parent,false);
        return new CartViewModel(cartBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewModel holder, int position) {
        CartResponse.Carts cart=cartList.get(position);
        holder.cartBinding.setCarts(cart);
        int i=0;
        total=0;

        price_value=Double.parseDouble(cartList.get(position).getPrice());



        holder.cartBinding.elegantCount.setNumber(cartList.get(position).getQuantity());
        holder.cartBinding.elegantCount.setOnValueChangeListener((view, oldValue, newValue) -> {
            //getTotal();
            String quantity=holder.cartBinding.elegantCount.getNumber();
            String item_id=cartList.get(position).getItem_id();
            int count=Integer.parseInt(quantity);
            price=Double.parseDouble(cartList.get(position).getPrice());
           // totals=totals+price_value*count;

            holder.cartBinding.textPrice.setText(String.valueOf(calculatePrice(price,count)));
            String amount=String.valueOf(price);
            //getTotal(total);
            listener.onActionPerformed(item_id,quantity,amount);


        });

        holder.cartBinding.textDelete.setOnClickListener(view -> {
          String item_id=cartList.get(position).getItem_id();
          deleteListener.onDelete(user_id,item_id);
        });

//        holder.cartBinding.icAdd.setOnClickListener(v -> {
//            int quantity=Integer.parseInt(cartList.get(position).getQuantity());
//            quantity++;
//            cartList.
////            String item_id=cartList.get(position).getItem_id();
////            holder.cartBinding.productCount.setText(String.valueOf(quantity));
////            price=Integer.parseInt(cartList.get(position).getPrice());
////            totals=totals+price*quantity;
////            holder.cartBinding.textPrice.setText(String.valueOf(calculatePrice(price,quantity)));
////
////            //getTotal(total);
////            listener.onActionPerformed(item_id,String.valueOf(quantity));
//        });

    }

    private double  calculatePrice(double priceValue, int quantity)
    {
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

    public void restoreItem(CartResponse.Carts item, int position) {
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
                List<CartResponse.Carts> filterList = new ArrayList<>();
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
            cartList = (List<CartResponse.Carts>) results.values;
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

    public interface setOnActionListener{
      void onActionPerformed(String item_id,String quantity,String price);
    }

//    public interface UpdatePayableAmount {
//        void updatePayableAmount(List<CartResponse.Carts> shoppingCart);
//    }
    public void setActionListener(setOnActionListener listener)
    {
        this.listener=listener;
    }

    public interface onDeleteListener{
        void onDelete(String userId,String itemId);
    }
    public void setDeleteListener(onDeleteListener listener)
    {
        this.deleteListener=listener;
    }

}
