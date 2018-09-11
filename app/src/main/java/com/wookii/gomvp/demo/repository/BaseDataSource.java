package com.wookii.gomvp.demo.repository;

import android.util.Pair;

import com.wookii.gomvp.utils.RetrofitConverter;
import com.wookii.gomvp.respository.GoDataSource;

import io.reactivex.Observable;
import io.reactivex.Observer;


/**
 * 业务中间层，可以设置success code 和 具体的RetrofitConverter对象，
 * Created by wuchen on 2018/1/10.
 */

public abstract class BaseDataSource implements GoDataSource {

    protected Observable observable;
    protected Observer observer;
    protected Class clazz;

    protected abstract <T,B> void getObject(Observable<T> o, Observer s, Class<B> clazz);

    /**
     * 空实现，这部分由子类重写
     * @return
     */
    @Override
    public GoDataSource loadDataFromRepository() {
        return null;
    }


    @Override
    public void onObservable(Observable observable) {
        this.observable = observable;
    }

    @Override
    public void onObserver(Observer observer) {
        this.observer = observer;
    }

    @Override
    public <T> void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * 空实现，这部分由子类重写
     * @return
     */
    @Override
    public Pair onSuccessCode() {
        return null;
    }

    /**
     * 空实现，这部分由子类重写
     * @return
     */
    @Override
    public RetrofitConverter onCreateRetrofitConverter() {
        return null;
    }
}
