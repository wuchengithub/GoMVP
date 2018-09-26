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
import com.wookii.gomvp.presenter.GoPresenterImpl;
import com.wookii.gomvp.respository.GoDataSource;

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
    public LifecyclePresenter(Context context) {
        super(context);
        super.context = context;
    }
    public LifecyclePresenter(Fragment fragment) {
        super();
        this.fragment = fragment;
        context = fragment.getContext();
        registerLifecycle(fragment.getLifecycle());

    }
    private void registerLifecycle(Lifecycle lifecycle){
        this.lifecycle = lifecycle;
        lifecycle.addObserver(this);
    }

    public LifecyclePresenter(AppCompatActivity activity) {
        this.activity = activity;
        context = activity.getApplicationContext();
        registerLifecycle(activity.getLifecycle());
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void create(){
        if (fragment != null) {
            context = fragment.getActivity().getApplicationContext();
        } else if(activity != null) {
            context = activity.getApplicationContext();
        }
        if(lifecycleListener != null) {
            lifecycleListener.onCreate(context);
        }
        GoLog.D("AssistantPresenter init success:");
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void starts(){
        if (fragment != null) {
            view = fragment.getView();
            unbinder = ButterKnife.bind(fragment, view);
        } else if(activity != null) {
            unbinder = ButterKnife.bind(activity);
        }
        GoLog.D("AssistantPresenter onStart:" + view);
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
        unbindPresenterAdapter();
        GoLog.D("AssistantPresenter onDestroy");
    }


    /**
     * 请求
     * @param presenterAdapter
     */
    public void execute(PresenterAdapter presenterAdapter) {
        if(onModel() == null) {
            throw new RuntimeException("repository is null, you should be set it !");
        }
        onModel().targetClazz(presenterAdapter.targetBeanType());
        super.request(presenterAdapter);
    }

    @Override
    public GoDataSource onModel() {
        return getModel();
    }

    @Override
    public T getValue() {
        return value;
    }


}
