package com.example.laundryapp.user.viewModel;

import com.example.laundryapp.user.repository.OrderRepository;
import com.example.laundryapp.user.response.OrderResponse;
import com.example.laundryapp.utilities.CommonResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class OrderViewModel extends ViewModel {
    public OrderRepository orderRepository;

    public OrderViewModel() {
        this.orderRepository=new OrderRepository();
    }

    public LiveData<CommonResponse> addOrder(int user_id,String service_name,String item_name,int total_price,String address){
        return orderRepository.addOrder(user_id, service_name, item_name, total_price, address);
    }

    public LiveData<OrderResponse> getOrders(int user_id){
        return orderRepository.getOrders(user_id);
    }


}
