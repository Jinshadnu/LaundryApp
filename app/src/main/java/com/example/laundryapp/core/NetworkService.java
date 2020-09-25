package com.example.laundryapp.core;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;
    private static int REQUEST_TIME_OUT=20;

    public static Retrofit getRetrofitInstance(){
        if (retrofit == null){
            retrofit=new Retrofit.Builder()
                    .baseUrl(APIBase.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(providesClient())
                    .build();
        }
        return retrofit;
    }

    public static OkHttpClient providesClient(){
        okHttpClient=new OkHttpClient.Builder()
                .connectTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIME_OUT,TimeUnit.SECONDS)
                .build();

        return okHttpClient;
    }

}
