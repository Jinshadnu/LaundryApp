package com.example.laundryapp.fragments.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.LayoutCategoryBinding;
import com.example.laundryapp.fragments.pojo.Categories;
import com.example.laundryapp.user.pojo.ServiceResponse;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import static android.view.LayoutInflater.from;

public class CategoriesAdapater extends RecyclerView.Adapter<CategoriesAdapater.CategoriesViewHolder> {
    public Context  context;
    public List<ServiceResponse.OurServices.Categorise> categoriesList;
    public LayoutCategoryBinding categoryBinding;
    private int row_index = 0;
    private ItemClickListener clickListener;

    public CategoriesAdapater(Context context, List<ServiceResponse.OurServices.Categorise> categoriesList) {
        this.context = context;
        this.categoriesList = categoriesList;
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        categoryBinding= DataBindingUtil.inflate(from(context), R.layout.layout_category,parent,false);
        return new CategoriesViewHolder(categoryBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
        ServiceResponse.OurServices.Categorise categories=categoriesList.get(position);
        holder.categoryBinding.setCategories(categories);


        if(row_index == position){
            holder.categoryBinding.relativeLayout.setBackgroundColor(Color.parseColor("#253a95"));
            holder.categoryBinding.textCategory.setTextColor(Color.parseColor("#253a95"));
        }else{
            holder.categoryBinding.relativeLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.categoryBinding.textCategory.setTextColor(Color.parseColor("#253a95"));
        }

    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public class CategoriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public LayoutCategoryBinding categoryBinding;
        public CategoriesViewHolder(@NonNull LayoutCategoryBinding categoryBinding) {
            super(categoryBinding.getRoot());

            this.categoryBinding=categoryBinding;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            row_index = getAdapterPosition();
            if (clickListener != null) clickListener.onClick(view, getPosition());
            notifyDataSetChanged();
        }
        }
    public interface ItemClickListener {
        void onClick(View view, int position);
    }
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

}

