package com.wookii.gomvp.demo.simple;

import android.content.Context;
import android.util.Pair;

import com.wookii.gomvp.adapter.PresenterAdapter;
import com.wookii.gomvp.demo.api.ApiServer;
import com.wookii.gomvp.demo.bean.HttpResult;
import com.wookii.gomvp.demo.bean.MarketBean;
import com.wookii.gomvp.utils.RetrofitConverter;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public class MarketPresenterAdapter extends PresenterAdapter {
    @Override
    public Observable onCreateObservable(Context context, RetrofitConverter retrofitConverter) {
        Retrofit retrofit = retrofitConverter.createRetrofit();
        ApiServer apiServer = retrofit.create(ApiServer.class);

        HashMap<String, Object> map = new HashMap<>();
        map.put("请求参数1",0);
        map.put("请求参数2","123");
        Observable<HttpResult<Object>> observable = apiServer.getMethods(map);

        return observable;
    }

    @Override
    public Pair onSuccessCodePair() {
        return new Pair("success","true");
    }

    @Override
    public String onErrorMessageKey() {
        return "message";
    }

    @Override
    public Class targetBeanType() {
        return MarketBean.class;
    }
}
