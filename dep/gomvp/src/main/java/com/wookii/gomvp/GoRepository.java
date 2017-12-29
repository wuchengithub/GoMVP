package com.wookii.gomvp;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

import static android.content.ContentValues.TAG;
import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;


/**
 * Created by wuchen on 2017/11/10.
 */

public class GoRepository<T> implements GoDataSource {

    private Cacher cacher;
    private Observable<T> observable;

    List<Observer> observers = new ArrayList<>();

    public GoRepository(Observer observer) {
        addContentObserver(observer);
    }

    private GoRepository() {

    }

    private GoRepository(Observable observable, Cacher cacher) {
        this.observable = observable;
        this.cacher = cacher;
    }

    public static final class Builder<T> {

        private Observable<T> observable;
        private Cacher cacher;

        public Builder<T> retrofit(Observable observable) {
            this.observable = observable;
            return this;
        }

        public GoRepository build() {
            return new GoRepository<T>(observable, cacher);
        }

        public Builder<T> cache(Cacher cacher) {
            this.cacher = cacher;
            return this;
        }
    }

    @Override
    public GoDataSource loadDataFromRepository() {
        checkNotNull(observable);
        try {
            observable.subscribeOn(io.reactivex.schedulers.Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<T>() {
                @Override
                public void accept(T t) throws Exception {
                    Log.i(TAG, t.getClass().getGenericSuperclass().toString());
                    for (Observer observer :
                            observers) {
                        observer.onNext(t);
                    }
                }
            });
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public Cacher getCacher() {
        return cacher;
    }


    @Override
    public void addContentObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeContentObserver(Observer observer) {
        if (observers.contains(observer)) {
            observers.remove(observer);
        }
    }

    private void notifyContentObserver() {
        for (Observer observer : observers) {
            //observer.onNext();
        }
    }
}
