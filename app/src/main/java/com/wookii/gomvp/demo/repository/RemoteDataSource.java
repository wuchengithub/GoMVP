package com.wookii.gomvp.demo.repository;

import android.content.Context;

import com.wookii.gomvp.cache.DefaultGoCache;
import com.wookii.gomvp.respository.GoDataSource;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by wuchen on 2018/1/10.
 */

public class RemoteDataSource extends BaseDataSource {

    private static RemoteDataSource INSTANCE;
    protected final Context context;

    public RemoteDataSource(Context context) {
        this.context = context.getApplicationContext();
    }

    public static synchronized RemoteDataSource getInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = new RemoteDataSource(context);
        }
        return INSTANCE;
    }
    @Override
    public synchronized <T, B> void getObject(Observable<T> o, Observer s, Class<B> clazz) {
        //LogUtils.E(".......................in to getObject..............");
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    @Override
    public GoDataSource.GoCache getGoCache() {
        return new DefaultGoCache(context);
    }

}
