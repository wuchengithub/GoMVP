package com.wookii.gomvp.respository;

import android.util.Pair;

import com.wookii.gomvp.utils.RetrofitConverter;

import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Created by wuchen on 2017/11/10.
 */

public interface GoDataSource {
    GoDataSource loadDataFromRepository();

    void onObserver(Observer observer);

    GoCache getGoCache();

    void onObservable(Observable observable);

    RetrofitConverter onCreateRetrofitConverter();

    Pair onSuccessCode();

    <T> void setClazz(Class<T> clazz);

    interface GoCache<K,T> {
        void onAdd(K key, T value);
        T onGet(K key);
    }
}
