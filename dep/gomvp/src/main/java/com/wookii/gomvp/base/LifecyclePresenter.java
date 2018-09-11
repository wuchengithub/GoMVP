package com.wookii.gomvp.base;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wookii.gomvp.GoLog;
import com.wookii.gomvp.adapter.PresenterAdapter;
import com.wookii.gomvp.adapter.SinglePresenterAdapter;
import com.wookii.gomvp.presenter.GoPresenterImpl;
import com.wookii.gomvp.respository.GoDataSource;
import com.wookii.gomvp.view.BaseView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 增加生命周期感知，和ButterKnife
 * Created by wuchen on 2017/12/6.
 */

public class LifecyclePresenter<T> extends GoPresenterImpl<T> implements LifecycleObserver {

    private static final String TAG = "AssistantPresenter";
    private Lifecycle lifecycle;
    private Fragment fragment;
    private AppCompatActivity activity;
    private Unbinder unbinder;
    private View view;
    private Context context;
    public LifecyclePresenter(Context context) {
        super();
        this.context = context;
    }
    public LifecyclePresenter(Fragment fragment) {
        super();
        this.fragment = fragment;
        registerLifecycle(fragment.getLifecycle());

    }
    private void registerLifecycle(Lifecycle lifecycle){
        this.lifecycle = lifecycle;
        lifecycle.addObserver(this);
    }

    public LifecyclePresenter(AppCompatActivity activity) {
        super();
        this.activity = activity;
        this.context = activity.getApplicationContext();
        registerLifecycle(activity.getLifecycle());
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void create(){
        if (fragment != null) {
            this.context = fragment.getActivity().getApplicationContext();
        } else if(activity != null) {
            this.context = activity.getApplicationContext();
        }
        if(lifecycleListener != null) {
            lifecycleListener.onCreate(context);
        }
        GoLog.E("AssistantPresenter init success:");
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void starts(){
        if (fragment != null) {
            view = fragment.getView();
            unbinder = ButterKnife.bind(fragment, view);
        } else if(activity != null) {
            unbinder = ButterKnife.bind(activity);
        }
        GoLog.E("AssistantPresenter onStart:" + view);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void destroy(){
        if(unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
        if(lifecycle != null) {
            lifecycle.removeObserver(this);
        }
        setCreateAdapter(null);
        bindPresenterAdapter(null);
        GoLog.E("AssistantPresenter onDestroy");
    }

    /**
     * 请求
     * @param context
     * @param clazz 指定返回结果的类型
     */
    public void request(Context context, Class<T> clazz) {
        onModel().setClazz(clazz);
        super.request(context);
    }

    public void request(PresenterAdapter presenterAdapter, BaseView view, Class clazz) {
        setPresenterAdapter(presenterAdapter);
        setView(view);
        request(context,clazz);
    }

    public void request(SinglePresenterAdapter presenterAdapter) {
        setPresenterAdapter(presenterAdapter);
        setView(presenterAdapter);
        request(context,presenterAdapter.onClazz());
    }
    @Override
    public T getValue() {
        return value;
    }

    @Override
    public GoDataSource onModel() {
        return null;
    }

}
