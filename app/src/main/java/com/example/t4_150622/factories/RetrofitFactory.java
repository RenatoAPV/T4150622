package com.example.t4_150622.factories;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {
    public static Retrofit build(){
        return new Retrofit.Builder()
                .baseUrl("https://62a9bfdc3b314385543a9b73.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
