package com.wookii.gomvp.utils;

import retrofit2.Retrofit;

/**
 * Created by wuchen on 2018/3/14.
 */

public interface RetrofitConverter {

    String host();

    Retrofit createRetrofit();
}
