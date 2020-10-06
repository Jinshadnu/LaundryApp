package com.example.laundryapp.fragments.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.laundryapp.R;
import com.example.laundryapp.databinding.LayoutCategoryBinding;
import com.example.laundryapp.fragments.pojo.Categories;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import static android.view.LayoutInflater.from;

public class CategoriesAdapater extends RecyclerView.Adapter<CategoriesAdapater.CategoriesViewHolder> {
    public Context  context;
    public List<Categories> categoriesList;
    public LayoutCategoryBinding categoryBinding;

    public CategoriesAdapater(Context context, List<Categories> categoriesList) {
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
        Categories categories=categoriesList.get(position);
        holder.categoryBinding.setCategories(categories);

    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public class CategoriesViewHolder extends RecyclerView.ViewHolder {
        public LayoutCategoryBinding categoryBinding;
        public CategoriesViewHolder(@NonNull LayoutCategoryBinding categoryBinding) {
            super(categoryBinding.getRoot());

            this.categoryBinding=categoryBinding;

        }
    }
}
