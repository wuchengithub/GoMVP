package com.wookii.gomvp.demo.repository;

import com.wookii.gomvp.demo.api.ApiServer;
import com.wookii.gomvp.utils.RetrofitConverter;
import com.wookii.gomvp.demo.utils.XstoreRetrofitManager;

/**
 * Created by wuchen on 2018/3/14.
 */

public class MainRetrofit implements RetrofitConverter {
    @Override
    public String host() {
        return ApiServer.URL_CONTRACT_NET;
    }

    @Override
    public <T> T create(Class<T> t) {
        return XstoreRetrofitManager.getInstance().getRetrofit(host(),null).create(t);
    }
}
