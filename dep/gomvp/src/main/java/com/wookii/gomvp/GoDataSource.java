package com.wookii.gomvp;

import io.reactivex.Observer;

/**
 * Created by wuchen on 2017/11/10.
 */

public interface GoDataSource {
    GoDataSource loadDataFromRepository();

    void addContentObserver(Observer oberver);

    Cacher getCacher();

    interface Cacher<K,T> {
        void onAdd(K key, T value);
        T onGet(K key);
    }

}
