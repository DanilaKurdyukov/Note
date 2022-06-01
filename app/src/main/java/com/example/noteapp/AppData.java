package com.example.noteapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppData {
    private static Retrofit retrofit;
    public static Retrofit getClient(){

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.26:52326/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return  retrofit;
    }
}
