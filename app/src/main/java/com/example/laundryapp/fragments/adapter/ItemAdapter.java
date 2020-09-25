package com.example.laundryapp.fragments.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.LayoutItemsBinding;
import com.example.laundryapp.databinding.LayoutPlansBinding;
import com.example.laundryapp.fragments.pojo.Items;
import com.example.laundryapp.fragments.pojo.Plans;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import static android.view.LayoutInflater.from;
import static com.example.laundryapp.user.OrderActivity.getTotal;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemsViewViewHolder> {
    public Context context;
    public List<Items> itemsList;
    public int price,quantity,total;

    public ItemAdapter(Context context, List<Items> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
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


        holder.itemsBinding.elegantCount.setOnValueChangeListener((view, oldValue, newValue) -> {
            Toast.makeText(context.getApplicationContext(),"Total",Toast.LENGTH_LONG).show();
            //getTotal();
            for (int i= 0;i<itemsList.size();i++){
                String quantity=holder.itemsBinding.elegantCount.getNumber();
                int count=Integer.parseInt(quantity);
                price=itemsList.get(i).getProduct_price();
                total=total+price*count;
            }
            getTotal(total);
        });

    }



    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class ItemsViewViewHolder extends RecyclerView.ViewHolder {
        public LayoutItemsBinding itemsBinding;
        public ItemsViewViewHolder(@NonNull LayoutItemsBinding layoutItemsBinding) {
            super(layoutItemsBinding.getRoot());
            this.itemsBinding=layoutItemsBinding;
        }
    }
}
