package com.example.laundryapp.fragments.viewmodel;

import com.example.laundryapp.fragments.pojo.Orders;
import com.example.laundryapp.fragments.repository.OrderRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class OrderViewModel extends ViewModel {
    public OrderRepository orderRepository;
    public OrderViewModel() {
        this.orderRepository=new OrderRepository();
    }

    public LiveData<List<Orders>> fetchOrders(){
        return orderRepository.fetchOrders();
    }
}
