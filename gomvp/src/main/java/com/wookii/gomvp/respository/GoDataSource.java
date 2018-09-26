package com.wookii.gomvp.respository;

import android.content.Context;

import com.wookii.gomvp.utils.RetrofitConverter;

import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Created by wuchen on 2017/11/10.
 */

public interface GoDataSource {
    <B> GoDataSource loadDataFromRepository(Observable<B> observable, Observer observer);

    GoCache getGoCache(Context context);

    RetrofitConverter onCreateRetrofitConverter();

    <T> void targetClazz(Class<T> clazz);

    interface GoCache<K,T> {
        void onAdd(K key, T value);
        T onGet(K key);
    }
}
