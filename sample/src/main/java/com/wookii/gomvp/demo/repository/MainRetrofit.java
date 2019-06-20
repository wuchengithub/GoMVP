package com.wookii.gomvp.demo.repository;

import com.wookii.gomvp.demo.api.ApiServer;
import com.wookii.gomvp.utils.RetrofitConverter;
import com.wookii.gomvp.demo.utils.RetrofitManager;

import retrofit2.Retrofit;

/**
 * Created by wuchen on 2018/3/14.
 */

public class MainRetrofit implements RetrofitConverter {
    @Override
    public String host() {
        return ApiServer.YOU_URL_CONTRACT_NET;
    }

    @Override
    public Retrofit createRetrofit() {
        return RetrofitManager.getInstance().getRetrofit(host(),null);
    }
}
