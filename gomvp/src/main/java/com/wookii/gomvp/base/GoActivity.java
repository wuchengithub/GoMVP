package com.wookii.gomvp.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wookii.gomvp.GoMVP;
import com.wookii.gomvp.adapter.PresenterAdapter;
import com.wookii.gomvp.presenter.GoPresenterImpl;
import com.wookii.gomvp.view.ExecuteStatusView;

/**
 * Created by wuchen on 2018/4/4.
 */

public abstract class GoActivity<T> extends AppCompatActivity implements GoView<T>, GoPresenterImpl.LifecycleListener{

    private LifecyclePresenter p_presenter;
    private PresenterAdapter presenterAdapter;

    @SuppressLint("ResourceType")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GoMVP goMVP = new GoMVP.Builder()
                .view(this)
                .presenter(new LifecyclePresenter(this))
                .build();
        this.p_presenter = goMVP.getPresenter();
        p_presenter.addLifecycleListener(this);
        presenterAdapter = onPresenterAdapter(getApplicationContext());

        checkPresenterNull();
        if(presenterAdapter == null) {
            throw new RuntimeException("Adapter 还没有准备好！尝试在view create完成后就行调用");
        }
        p_presenter.bindPresenterAdapter(presenterAdapter);
    }

    @Override
    public void onCreate(Context context) {

    }

    protected  void request(Class clazz) {
        checkPresenterNull();
        p_presenter.execute(presenterAdapter);
    }

    private void checkPresenterNull() {
        if(p_presenter == null) {
            throw new RuntimeException("presenter 还没有准备好！尝试在view create完成后就行调用");
        }
    }

    protected  void registerLoadingView(ExecuteStatusView listener) {
        checkPresenterNull();
        p_presenter.registerExecuteStatus(listener);
    }
    public LifecyclePresenter getPresenter() {
        return p_presenter;
    }

    protected abstract PresenterAdapter onPresenterAdapter(Context context);
}
