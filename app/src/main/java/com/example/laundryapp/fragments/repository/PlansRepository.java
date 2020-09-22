package com.example.laundryapp.fragments.repository;

import com.example.laundryapp.R;
import com.example.laundryapp.fragments.pojo.Plans;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class PlansRepository {

    public PlansRepository() {

    }

    public LiveData<List<Plans>> fetchPlans(){
        MutableLiveData mutableLiveData=new MutableLiveData();

        List<Plans> plansList=new ArrayList<>();
        plansList.add(new Plans(1, R.drawable.plan1));
        plansList.add(new Plans(2,R.drawable.plan2));
        plansList.add(new Plans(3,R.drawable.plan1));

        mutableLiveData.setValue(plansList);

        return mutableLiveData;
    }
}
