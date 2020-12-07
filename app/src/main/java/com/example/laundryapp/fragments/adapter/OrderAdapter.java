package com.example.laundryapp.fragments.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.LayoutOredersBinding;
import com.example.laundryapp.databinding.LayoutPlansBinding;
import com.example.laundryapp.fragments.pojo.Orders;
import com.example.laundryapp.fragments.pojo.Plans;
import com.example.laundryapp.user.pojo.ServiceResponse;
import com.example.laundryapp.user.response.CartResponse;
import com.example.laundryapp.user.response.OrderResponse;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import static android.view.LayoutInflater.from;
import static java.util.Locale.getDefault;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder>  {
    public Context context;
    public List<OrderResponse.Order> ordersList;
    public List<OrderResponse.Order> searchorderList;


    public OrderAdapter(Context context, List<OrderResponse.Order> ordersList) {
        this.context = context;
        this.ordersList = ordersList;
        this.searchorderList = new ArrayList<>(ordersList);
    }



    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutOredersBinding oredersBinding= DataBindingUtil.inflate(from(context), R.layout.layout_oreders,parent,false);
        return new OrderViewHolder(oredersBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderResponse.Order orders=ordersList.get(position);
        holder.oredersBinding.setOrder(orders);

        holder.oredersBinding.textCancel.setOnClickListener(view -> {
            AlertDialog.Builder alertDialog=new AlertDialog.Builder(context);
            alertDialog.setTitle("Cancel Order");
            alertDialog.setMessage("Are you sure want to cancel this Order ? ");

            alertDialog.setPositiveButton("Yes",(dialogInterface, i) -> {

            });

            alertDialog.setNegativeButton("No",(dialogInterface, i) -> {
             dialogInterface.cancel();
            });

            alertDialog.show();


        });


    }

    public void filter(String charText) {
        charText = charText.toLowerCase(getDefault());

        ordersList.clear();

        if (charText.length() == 0)
            ordersList.addAll(searchorderList);
        else
            for (OrderResponse.Order order : searchorderList)
                if (order.getDate().toLowerCase().contains(charText.toLowerCase()))
                    ordersList.add(order);

        notifyDataSetChanged();
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
