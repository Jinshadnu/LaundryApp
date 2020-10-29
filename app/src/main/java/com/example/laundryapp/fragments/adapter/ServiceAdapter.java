package com.example.laundryapp.fragments.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.LayoutPlansBinding;
import com.example.laundryapp.databinding.LayoutServiceBinding;
import com.example.laundryapp.fragments.pojo.Plans;
import com.example.laundryapp.fragments.pojo.Services;
import com.example.laundryapp.user.DetailsActivity;
import com.example.laundryapp.user.pojo.ServiceResponse;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import static android.view.LayoutInflater.from;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {
    public Context context;
    public List<ServiceResponse.OurServices> servicesList;

    public ServiceAdapter(Context context, List<ServiceResponse.OurServices> servicesList) {
        this.context = context;
        this.servicesList = servicesList;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutServiceBinding serviceBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_service,parent,false);
        return new ServiceViewHolder(serviceBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        ServiceResponse.OurServices services=servicesList.get(position);
        holder.serviceBinding.setServices(services);


        holder.serviceBinding.cardViewServices.setOnClickListener(v -> {
            Intent intent = new Intent(context.getApplicationContext(), DetailsActivity.class);
            String description=servicesList.get(position).getService_description();
            String service_name=servicesList.get(position).getService_name();
            int pos=holder.getAdapterPosition();
            intent.putExtra("description",description);
            intent.putExtra("position",String.valueOf(pos));
            intent.putExtra("service_name",service_name);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
