package com.example.laundryapp.fragments.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.LayoutOredersBinding;
import com.example.laundryapp.databinding.LayoutPlansBinding;
import com.example.laundryapp.fragments.pojo.Orders;
import com.example.laundryapp.fragments.pojo.Plans;
import com.example.laundryapp.user.OrderedItemsActivity;
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
    public cancelOrderListener cancelOrderListener;
    public String order_id,order_status;
    public int postion=0;


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
        order_status=orders.getOrder_status();

        if (order_status.equals("APPROVED")){
            holder.oredersBinding.textCancel.setVisibility(View.GONE);
            holder.oredersBinding.viewCancel.setVisibility(View.GONE);
        }
        if (order_status.equals("CANCELLED")){
            holder.oredersBinding.textCancel.setVisibility(View.GONE);
            holder.oredersBinding.viewCancel.setVisibility(View.GONE);
        }
        if (order_status.equals("DISPATCHED")){
            holder.oredersBinding.textCancel.setVisibility(View.GONE);
            holder.oredersBinding.viewCancel.setVisibility(View.GONE);
        }





        holder.oredersBinding.textCancel.setOnClickListener(view -> {
            order_id=orders.getOrder_id();


            AlertDialog.Builder alertDialog=new AlertDialog.Builder(context);
            alertDialog.setTitle("Cancel Order");
            alertDialog.setMessage("Are you sure want to cancel this Order ? ");

            alertDialog.setPositiveButton("Yes",(dialogInterface, i) -> {
                cancelOrderListener.onOrderCancel(order_id);
            });

            alertDialog.setNegativeButton("No",(dialogInterface, i) -> {
             dialogInterface.cancel();
            });

            alertDialog.show();


        });

        holder.oredersBinding.textViewmore.setOnClickListener(view -> {
            Intent intent=new Intent(context.getApplicationContext(), OrderedItemsActivity.class);
            postion=holder.getAdapterPosition();
            OrderResponse.Order order = ordersList.get(position);
            intent.putExtra("orderId",order.getOrder_id());
            context.startActivity(intent);
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

    public interface cancelOrderListener{
     void onOrderCancel(String orderId);
    }
    public void setCancelListener(cancelOrderListener listener){
        this.cancelOrderListener=listener;
    }
}
