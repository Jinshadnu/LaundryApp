package com.example.laundryapp.user.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.LayoutAddressBinding;
import com.example.laundryapp.fragments.adapter.CategoriesAdapater;
import com.example.laundryapp.user.response.AddressResponse;

import java.util.List;

import static android.view.LayoutInflater.from;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {
    public Context context;
    public List<AddressResponse.Addres> addresList;
    public LayoutAddressBinding addressBinding;

    public AddressAdapter(Context context, List<AddressResponse.Addres> addresList) {
        this.context = context;
        this.addresList = addresList;
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        addressBinding= DataBindingUtil.inflate(from(context), R.layout.layout_address,parent,false);
        return new AddressAdapter.AddressViewHolder(addressBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
       AddressResponse.Addres addres=addresList.get(position);
       holder.addressBinding.setAddress(addres);
    }

    @Override
    public int getItemCount() {
        return addresList.size();
    }

    public class AddressViewHolder extends RecyclerView.ViewHolder {
        public LayoutAddressBinding addressBinding;
        public AddressViewHolder(@NonNull LayoutAddressBinding addressBinding) {
            super(addressBinding.getRoot());
            this.addressBinding=addressBinding;
        }
    }
}
