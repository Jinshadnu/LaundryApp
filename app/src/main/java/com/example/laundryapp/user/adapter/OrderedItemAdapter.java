package com.example.laundryapp.user.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.example.laundryapp.R;



import com.example.laundryapp.databinding.LayoutOrderitemsBinding;
import com.example.laundryapp.user.pojo.OrderedItemResponse;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import static android.view.LayoutInflater.from;

public class OrderedItemAdapter extends RecyclerView.Adapter<OrderedItemAdapter.OrderViewHolder> {
    public Context context;
    public List<OrderedItemResponse.OrderItems> orderItemsList;


    public OrderedItemAdapter(Context context, List<OrderedItemResponse.OrderItems> itemResponses) {
        this.context = context;
        this.orderItemsList = itemResponses;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutOrderitemsBinding orderedItemsBinding= DataBindingUtil.inflate(from(context), R.layout.layout_orderitems,parent,false);
        return new OrderViewHolder(orderedItemsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
     OrderedItemResponse.OrderItems itemResponse=orderItemsList.get(position);
     holder.orderedItemsBinding.setOrdereditems(itemResponse);

    }

    @Override
    public int getItemCount() {
        return orderItemsList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        public LayoutOrderitemsBinding orderedItemsBinding;
        public OrderViewHolder(@NonNull LayoutOrderitemsBinding orderedItemsBinding) {
            super(orderedItemsBinding.getRoot());
            this.orderedItemsBinding=orderedItemsBinding;
        }
    }
}
