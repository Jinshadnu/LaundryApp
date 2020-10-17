package com.example.laundryapp.fragments.repository;

import com.example.laundryapp.R;
import com.example.laundryapp.core.NetworkAPI;
import com.example.laundryapp.core.NetworkService;
import com.example.laundryapp.fragments.pojo.Cart;
import com.example.laundryapp.fragments.pojo.Items;
import com.example.laundryapp.utilities.CommonResponse;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartRepostory {

    public NetworkAPI networkAPI;

    public CartRepostory() {
    }

    public LiveData<List<Cart>> getcartItems(){
        MutableLiveData mutableLiveData=new MutableLiveData();

        List<Cart> cartList=new ArrayList<>();
        cartList.add(new Cart("T-shirt",R.drawable.t_shirt,5,1));
        cartList.add(new Cart("Shirt", R.drawable.shirt,7,1));
        cartList.add(new Cart("Pant", R.drawable.pant,10,1));
        cartList.add(new Cart("Shorts",R.drawable.shorts,4,1));
        cartList.add(new Cart("Shirt", R.drawable.shirt,7,1));
        cartList.add(new Cart("T-shirt",R.drawable.t_shirt,5,1));
        cartList.add(new Cart("Shorts",R.drawable.shorts,4,1));

        mutableLiveData.setValue(cartList);

        return mutableLiveData;
    }

//    public LiveData<CommonResponse> addToCart(int id){
//        MutableLiveData mutableLiveData=new MutableLiveData();
//
//        networkAPI= NetworkService.getRetrofitInstance().create(NetworkAPI.class);
//
//        Call<CommonResponse> responseCall=networkAPI.addToCart(id);
//        responseCall.enqueue(new Callback<CommonResponse>() {
//            @Override
//            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
//                CommonResponse commonResponse=response.body();
//                if (commonResponse != null){
//                    mutableLiveData.postValue(commonResponse);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CommonResponse> call, Throwable t) {
//             mutableLiveData.postValue(null);
//            }
//        });
//
//        return mutableLiveData;
//    }
}
