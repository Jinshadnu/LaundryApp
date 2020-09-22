package com.example.laundryapp.fragments.repository;

import com.example.laundryapp.R;
import com.example.laundryapp.fragments.pojo.Items;
import com.example.laundryapp.fragments.pojo.Orders;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ItemsRepository {

    public ItemsRepository() {
    }

    public LiveData<List<Items>> getItems(){
        MutableLiveData mutableLiveData=new MutableLiveData();

        List<Items> itemsList=new ArrayList<>();
        itemsList.add(new Items("T-shirt",5, R.drawable.t_shirt));
        itemsList.add(new Items("Shirt",10, R.drawable.shirt));
        itemsList.add(new Items("Pant",15, R.drawable.pant));
        itemsList.add(new Items("Shorts",5, R.drawable.shorts));

        mutableLiveData.setValue(itemsList);

        return mutableLiveData;
    }
}
