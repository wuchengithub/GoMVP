package com.wookii.gomvp.presenter;

import android.content.Context;

import com.wookii.gomvp.GoLog;
import com.wookii.gomvp.adapter.PresenterAdapter;
import com.wookii.gomvp.respository.GoDataSource;
import com.wookii.gomvp.view.IGoView;

import io.reactivex.Observer;

/**
 * Created by wuchen on 2017/11/10.
 */

public interface GoPresenter<T, V extends IGoView,M extends GoDataSource> {

    void setView(V view);

    V getView();

    M onModel();

    M loadData();

    T getValue();

    String getResponseString();

    void setRepository(GoDataSource model);

    void setObserver(Observer observer);

    void setCreateAdapter(PresenterAdapter onCreateDataLoader);

    GoPresenter bindPresenterAdapter(PresenterAdapter presenterAdapter);

    void request(Context context);

    T getCache(Class<T> clazz);

    void setLog(GoLog log);
}
