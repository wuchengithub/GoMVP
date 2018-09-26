package com.wookii.gomvp.adapter;


import com.wookii.gomvp.presenter.GoPresenterImpl;
import com.wookii.gomvp.view.BaseView;

public abstract class SinglePresenterAdapter<T> extends PresenterAdapter implements BaseView<GoPresenterImpl<T>> {

    public abstract Class onClazz();
}
