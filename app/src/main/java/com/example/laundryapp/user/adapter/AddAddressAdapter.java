package com.example.laundryapp.user.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.LayoutAddaddressBinding;
import com.example.laundryapp.user.response.AddressResponse;

import java.util.List;

import static android.view.LayoutInflater.from;

public class AddAddressAdapter extends RecyclerView.Adapter<AddAddressAdapter.AddViewHolder> {
    public Context context;
    public List<AddressResponse.Addres> addresList;
    public LayoutAddaddressBinding addaddressBinding;

    public AddAddressAdapter(Context context, List<AddressResponse.Addres> addresList) {
        this.context = context;
        this.addresList = addresList;
    }

    @NonNull
    @Override
    public AddViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        addaddressBinding= DataBindingUtil.inflate(from(context), R.layout.layout_addaddress,parent,false);
        return new AddAddressAdapter.AddViewHolder(addaddressBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AddViewHolder holder, int position) {
        AddressResponse.Addres addres=addresList.get(position);
        holder.addaddressBinding.setAddress(addres);
    }

    @Override
    public int getItemCount() {
        return addresList.size();
    }

    public class AddViewHolder extends RecyclerView.ViewHolder {
        public LayoutAddaddressBinding addaddressBinding;
        public AddViewHolder(@NonNull LayoutAddaddressBinding addaddressBinding) {
            super(addaddressBinding.getRoot());
            this.addaddressBinding=addaddressBinding;
        }
    }
}
