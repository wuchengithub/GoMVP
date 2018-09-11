package com.wookii.gomvp.demo.repository;

import android.content.Context;

import com.wookii.gomvp.cache.ObjectGoCache;

import io.reactivex.Observable;
import io.reactivex.Observer;


/**
 * Created by wuchen on 2018/1/10.
 */

public class LocalDataSource extends BaseDataSource {
    private static LocalDataSource INSTANCE;
    private Context context;

    private LocalDataSource() {
    }

    private LocalDataSource(Context context) {
        this.context = context.getApplicationContext();
    }

    public static synchronized LocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public <T, B> void getObject(Observable<T> o, Observer s, Class<B> clazz) {
        ObjectGoCache cacher = (ObjectGoCache) getGoCache();
        B b = cacher.getObject(clazz);
        if(b != null) {
            s.onNext(b);
        } else {
            s.onError(new Exception("数据获取失败"));
        }
    }
    @Override
    public GoCache getGoCache() {
        return new ObjectGoCache(context);
    }

}
