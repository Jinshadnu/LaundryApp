package com.example.laundryapp.user.viewModel;

import com.example.laundryapp.user.repository.OrderRepository;
import com.example.laundryapp.user.response.ComonResponse;
import com.example.laundryapp.user.response.OrderResponse;
import com.example.laundryapp.utilities.CommonResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class OrderViewModel extends ViewModel {
    public OrderRepository orderRepository;

    public OrderViewModel() {
        this.orderRepository=new OrderRepository();
    }

    public LiveData<ComonResponse> addOrder(String user_id, String building_number, String steet_number, String zone,String latitude,String longitude,String address){
        return orderRepository.addOrder(user_id, building_number, steet_number, zone,latitude
                ,longitude,address);
    }

    public LiveData<OrderResponse> getOrders(String user_id){
        return orderRepository.getOrders(user_id);
    }


}
