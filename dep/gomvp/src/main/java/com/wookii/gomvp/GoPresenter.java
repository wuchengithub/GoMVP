package com.wookii.gomvp;

import android.content.Context;
import android.util.Pair;

import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Created by wuchen on 2017/11/10.
 */

public interface GoPresenter<T, V extends GoView,M extends GoDataSource> {

    void setView(V view);

    V getView();

    M createModel();

    M createModel(Observer oberver);

    M loadData();

    T getValue();

    void setModel(GoDataSource model);

    void setObserver(Observer observer);

    void setCreateAdapter(PresenterAdapter onCreateDataLoader);

    GoPresenter bindPresenterAdapter(PresenterAdapter presenterAdapter);

    void request(Context context);

    T getCache(Class<T> clazz);

    interface PresenterAdapter {
        Observable onCreateObservable(RetrofitUtils utils);

        Pair onSuccessCode();

        <T> void setPresenter(BasePresenter<T> tBasePresenter);
    }
}
