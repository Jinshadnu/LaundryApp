package com.example.laundryapp.fragments.viewmodel;

import com.example.laundryapp.fragments.pojo.Plans;
import com.example.laundryapp.fragments.repository.PlansRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class PlansViewModel extends ViewModel {
public PlansRepository plansRepository;
    public PlansViewModel() {
        this.plansRepository=new PlansRepository();
    }

    public LiveData<List<Plans>> getPlans(){
        return plansRepository.fetchPlans();
    }

}
