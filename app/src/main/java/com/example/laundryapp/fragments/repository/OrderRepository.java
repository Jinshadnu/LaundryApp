package com.example.laundryapp.fragments.repository;

import com.example.laundryapp.fragments.pojo.Orders;
import com.example.laundryapp.fragments.pojo.Plans;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class OrderRepository {

    public OrderRepository() {

    }

    public LiveData<List<Orders>> fetchOrders(){
        MutableLiveData mutableLiveData=new MutableLiveData();

        List<Orders> ordersList=new ArrayList<>();
        ordersList.add(new Orders("1010100","Jinshad","9526026636","Pending"));
        ordersList.add(new Orders("1010101","Jinshad","9526026636","Completed"));
        ordersList.add(new Orders("1010102","Jinshad","9526026636","Completed"));
        ordersList.add(new Orders("1010103","Jinshad","9526026636","Completed"));
        ordersList.add(new Orders("1010104","Jinshad","9526026636","Completed"));

        mutableLiveData.setValue(ordersList);

        return mutableLiveData;
    }
}
