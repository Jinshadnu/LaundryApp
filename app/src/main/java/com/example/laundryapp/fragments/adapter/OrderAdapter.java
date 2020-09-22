package com.example.laundryapp.fragments.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.LayoutOredersBinding;
import com.example.laundryapp.databinding.LayoutPlansBinding;
import com.example.laundryapp.fragments.pojo.Orders;
import com.example.laundryapp.fragments.pojo.Plans;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import static android.view.LayoutInflater.from;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    public Context context;
    public List<Orders> ordersList;

    public OrderAdapter(Context context, List<Orders> ordersList) {
        this.context = context;
        this.ordersList = ordersList;
    }



    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutOredersBinding oredersBinding= DataBindingUtil.inflate(from(context), R.layout.layout_oreders,parent,false);
        return new OrderViewHolder(oredersBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Orders orders=ordersList.get(position);
        holder.oredersBinding.setOrder(orders);
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        public LayoutOredersBinding oredersBinding;
        public OrderViewHolder(@NonNull LayoutOredersBinding oredersBinding) {
            super(oredersBinding.getRoot());
            this.oredersBinding=oredersBinding;
        }
    }
}
