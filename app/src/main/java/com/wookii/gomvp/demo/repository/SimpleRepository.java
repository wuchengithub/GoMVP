package com.wookii.gomvp.demo.repository;

import android.content.Context;

import com.wookii.gomvp.cache.DefaultGoCache;
import com.wookii.gomvp.demo.repository.MainRetrofit;
import com.wookii.gomvp.respository.GoDataSource;
import com.wookii.gomvp.utils.RetrofitConverter;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SimpleRepository implements GoDataSource {

    @Override
    public <B> GoDataSource loadDataFromRepository(Observable<B> observable, Observer observer) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return this;
    }

    @Override
    public GoCache getGoCache(Context context) {
        return new DefaultGoCache(context);
    }

    @Override
    public RetrofitConverter onCreateRetrofitConverter() {
        return new MainRetrofit();
    }

    @Override
    public <T> void targetClazz(Class<T> clazz) {

    }
}
