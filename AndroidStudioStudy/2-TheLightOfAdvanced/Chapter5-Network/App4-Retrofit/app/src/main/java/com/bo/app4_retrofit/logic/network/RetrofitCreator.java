package com.bo.app4_retrofit.logic.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitCreator {

    private static String BASE_URL="http://10.0.2.2/";
    private static final String TAO_BAO_IP_URL="http://ip.taobao.com/";
    //http://ip.taobao.com/outGetIpInfo?ip=14.28.41.120&accessKey=alibaba-inc

    private static final String HTTP_BIN_URL="https://www.httpbin.org/";

    public static Retrofit getRetrofitData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public static Retrofit getRetrofitIp(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TAO_BAO_IP_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public static Retrofit getRetrofitHttpBin(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HTTP_BIN_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
