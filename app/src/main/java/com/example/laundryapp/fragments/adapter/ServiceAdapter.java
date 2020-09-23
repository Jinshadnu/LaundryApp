package com.example.laundryapp.fragments.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.LayoutPlansBinding;
import com.example.laundryapp.databinding.LayoutServiceBinding;
import com.example.laundryapp.fragments.pojo.Plans;
import com.example.laundryapp.fragments.pojo.Services;
import com.example.laundryapp.user.DetailsActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import static android.view.LayoutInflater.from;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {
    public Context context;
    public List<Services> servicesList;

    public ServiceAdapter(Context context, List<Services> servicesList) {
        this.context = context;
        this.servicesList = servicesList;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutServiceBinding serviceBinding= DataBindingUtil.inflate(from(context), R.layout.layout_service,parent,false);
        return new ServiceViewHolder(serviceBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        Services services=servicesList.get(position);
        holder.serviceBinding.setServices(services);

        holder.serviceBinding.cardViewServices.setOnClickListener(v -> {
            Intent intent = new Intent(context.getApplicationContext(), DetailsActivity.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return servicesList.size();
    }

    public class ServiceViewHolder extends RecyclerView.ViewHolder {
        public LayoutServiceBinding serviceBinding;
        public ServiceViewHolder(@NonNull LayoutServiceBinding serviceBinding) {
            super(serviceBinding.getRoot());

            this.serviceBinding=serviceBinding;
        }
    }
}
