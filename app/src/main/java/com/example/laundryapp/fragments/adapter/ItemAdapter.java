package com.example.laundryapp.fragments.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.LayoutItemsBinding;
import com.example.laundryapp.databinding.LayoutPlansBinding;
import com.example.laundryapp.fragments.pojo.Items;
import com.example.laundryapp.fragments.pojo.Plans;
import com.example.laundryapp.user.OrderActivity;
import com.example.laundryapp.user.interfaces.AddCartCallBack;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import static android.view.LayoutInflater.from;
//import static com.example.laundryapp.user.OrderActivity.;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemsViewViewHolder> implements Filterable {
    public Context context;
    public List<Items> itemsList;
    List<Items> mStringFilterList;
    public int price,quantity,total;
    public ValueFilter valueFilter;

    public ItemAdapter(Context context, List<Items> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
        this.mStringFilterList=itemsList;
    }

    @NonNull
    @Override
    public ItemsViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutItemsBinding itemsBinding= DataBindingUtil.inflate(from(context), R.layout.layout_items,parent,false);
        return new ItemsViewViewHolder(itemsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewViewHolder holder, int position) {
        Items items=itemsList.get(position);
        holder.itemsBinding.setItems(items);

        //calculate price value



        holder.itemsBinding.elegantCount.setOnValueChangeListener((view, oldValue, newValue) -> {
            Toast.makeText(context.getApplicationContext(),"Total",Toast.LENGTH_LONG).show();
            //getTotal();
                String quantity=holder.itemsBinding.elegantCount.getNumber();
                int count=Integer.parseInt(quantity);
                price=itemsList.get(position).getProduct_price();
                total=total+price*count;
                holder.itemsBinding.textPrice.setText(String.valueOf(calculatePrice(price,count)));

            //getTotal(total);
        });

        holder.itemsBinding.buttonAddCart.setOnClickListener(v -> {
         if (context instanceof OrderActivity){
             ((AddCartCallBack)context).onAddProduct();
         }
        });

    }

    private int  calculatePrice(int priceValue, int quantity) {
        return priceValue * quantity;
    }



    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;

    }

    public class ItemsViewViewHolder extends RecyclerView.ViewHolder {
        public LayoutItemsBinding itemsBinding;
        public ItemsViewViewHolder(@NonNull LayoutItemsBinding layoutItemsBinding) {
            super(layoutItemsBinding.getRoot());
            this.itemsBinding=layoutItemsBinding;
        }
    }

    public class ValueFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                List<Items> filterList = new ArrayList<>();
                for (int i = 0; i < mStringFilterList.size(); i++) {
                    if ((mStringFilterList.get(i).product_name.toUpperCase()).contains(constraint.toString().toUpperCase())) {
                        filterList.add(mStringFilterList.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mStringFilterList.size();
                results.values = mStringFilterList;
            }
            return results;
        }

            @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
                itemsList = (List<Items>) results.values;
                notifyDataSetChanged();
        }
    }



}
