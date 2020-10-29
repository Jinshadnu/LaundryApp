package com.example.laundryapp.user.interfaces;

public interface AddCartCallBack {
    public void     onAddProduct();
    public void onRemoveProduct();
    public void addToCart(String item_id,String quantity,String price);

}
