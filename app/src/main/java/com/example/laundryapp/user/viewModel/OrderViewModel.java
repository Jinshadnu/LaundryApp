package com.example.laundryapp.user.viewModel;

import com.example.laundryapp.core.NetworkAPI;
import com.example.laundryapp.user.repository.AddOrderRepository;
import com.example.laundryapp.utilities.CommonResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class OrderViewModel extends ViewModel {
    public AddOrderRepository orderRepository;

    public OrderViewModel() {
        this.orderRepository=new AddOrderRepository();
    }

    public LiveData<CommonResponse> addOrder(int user_id,String service_name,String item_name,int total_price,String address){
        return orderRepository.addOrder(user_id, service_name, item_name, total_price, address);
    }
}
