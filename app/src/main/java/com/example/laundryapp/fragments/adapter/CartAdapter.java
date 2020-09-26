package com.example.laundryapp.fragments.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

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
import static com.example.laundryapp.fragments.CartFragment.getTotal;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewModel> implements Filterable {
    public Context context;
    public List<Cart> cartList;
    public List<Cart> cartListFiltered;
    public static List <Cart> selecteditems;
    public static int total=0;
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


        //String quantity=cartBinding.elegantCount.getNumber();
       // int quant=Integer.parseInt(quantity);
//        while (i<cartList.size()){
//            total=total + ( cartList.get(i).getPrice() * quant );
//            i++;
//        }
//        getTotal(total);

//        cartBinding.elegantCount.setOnValueChangeListener((view, oldValue, newValue) -> {
//            int j=0;
//            total=0;
//            String quantit=cartBinding.elegantCount.getNumber();
//            int quanti=Integer.parseInt(quantit);
//            while (j<cartList.size()){
//                total=total + ( cartList.get(j).getPrice() * quanti );
//                j++;
//            }
//            getTotal(total);
//        });


    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()){
                    cartListFiltered=cartList;
                }
                else {

                }
                return null;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

            }
        };
    }

    public class CartViewModel extends RecyclerView.ViewHolder {
        public LayoutCartBinding cartBinding;

        public CartViewModel(@NonNull LayoutCartBinding layoutCartBinding) {
            super(layoutCartBinding.getRoot());

            this.cartBinding=layoutCartBinding;
        }
    }


}
