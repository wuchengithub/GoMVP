package com.wookii.gomvp.demo.utils;


import com.wookii.gomvp.BuildConfig;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;

    private static HashMap<String, Retrofit> mRetrofits = new HashMap<>();


    public Retrofit getRetrofit(String baseUrl, Converter.Factory factory) {
        Retrofit retrofit = mRetrofits.get(baseUrl);
        if (null == retrofit) {

            OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
            okBuilder.addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request origin = chain.request();
                            Request.Builder requestBuilder = origin.newBuilder()
                                    .addHeader("type", "Android");
                            Request request = requestBuilder.build();
                            return chain.proceed(request);
                        }
                    });

            if(BuildConfig.DEBUG){
                okBuilder.addInterceptor(logging());
            }
            okBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

            retrofit = new Retrofit.Builder().client(okBuilder.build())
                    .addConverterFactory(new NullOnEmptyConverterFactory())
//                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(factory == null ? GsonConverterFactory.create() : factory)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(baseUrl)
                    .build();
            mRetrofits.put(baseUrl, retrofit);
        }
        return retrofit;
    }

    private HttpLoggingInterceptor logging() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }

    private RetrofitManager() {
    }


    private static class SingletonHolder {
        private static final RetrofitManager INSTANCE = new RetrofitManager();
    }

    //获取单例
    public static RetrofitManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

}
