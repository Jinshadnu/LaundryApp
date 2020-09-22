package com.example.laundryapp.fragments.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.LayoutPlansBinding;
import com.example.laundryapp.fragments.pojo.Plans;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import static android.view.LayoutInflater.from;

public class PlansAdapter extends RecyclerView.Adapter<PlansAdapter.PlansViewHolder> {
    public Context context;
    public List<Plans> plansList;

    public PlansAdapter(Context context, List<Plans> plansList) {
        this.context = context;
        this.plansList = plansList;
    }


    @NonNull
    @Override
    public PlansViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutPlansBinding plansBinding= DataBindingUtil.inflate(from(context), R.layout.layout_plans,parent,false);
        return new PlansViewHolder(plansBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PlansViewHolder holder, int position) {
    Plans plans=plansList.get(position);
    holder.plansBinding.setPlans(plans);
    }

    @Override
    public int getItemCount() {
        return plansList.size();
    }

    public class PlansViewHolder extends RecyclerView.ViewHolder {
        public LayoutPlansBinding plansBinding;
        public PlansViewHolder(@NonNull LayoutPlansBinding plansBinding) {
            super(plansBinding.getRoot());

            this.plansBinding=plansBinding;

        }
    }
}
