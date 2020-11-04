package com.example.laundryapp.user.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.LayoutAddressBinding;
import com.example.laundryapp.fragments.adapter.CartAdapter;
import com.example.laundryapp.fragments.adapter.CategoriesAdapater;
import com.example.laundryapp.user.response.AddressResponse;

import java.util.List;

import static android.view.LayoutInflater.from;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {
    public Context context;
    public List<AddressResponse.Addres> addresList;
    public LayoutAddressBinding addressBinding;
    public setOnAddressListener listener;
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

       holder.addressBinding.buttonOrder.setOnClickListener(v -> {
           String bulding_address=addresList.get(position).getBuilding_number();
           String street_address=addresList.get(position).getStreet_address();
           String zone=addresList.get(position).getZone_no();
           listener.onActionPerformed(bulding_address,street_address,zone);
       });
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

    public interface setOnAddressListener{
        void onActionPerformed(String building_number,String street_address,String zone);
    }

    public void setActionListener(AddressAdapter.setOnAddressListener listener)
    {
        this.listener=listener;
    }

}
