package com.example.laundryapp.fragments.viewmodel;

import com.example.laundryapp.fragments.pojo.Items;
import com.example.laundryapp.fragments.repository.ItemsRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class ItemsViewModel extends ViewModel {
    public ItemsRepository itemsRepository;

    public ItemsViewModel() {
        itemsRepository=new ItemsRepository();
    }

    public LiveData<List<Items>> fetchItems(){
        return itemsRepository.getItems();
    }


}
