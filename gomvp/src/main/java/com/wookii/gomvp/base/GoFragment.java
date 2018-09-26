package com.wookii.gomvp.base;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.wookii.gomvp.GoLog;
import com.wookii.gomvp.GoMVP;
import com.wookii.gomvp.adapter.PresenterAdapter;
import com.wookii.gomvp.presenter.GoPresenterImpl;
import com.wookii.gomvp.view.ExecuteStatusView;

/**
 * Created by wuchen on 2018/3/14.
 */

public abstract class GoFragment<T> extends Fragment implements GoView<T>, GoPresenterImpl.LifecycleListener {

    private static final String TAG = "AssistantFragment";
    private LifecyclePresenter p_presenter;
    private PresenterAdapter presenterAdapter;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        GoLog.D("setUserVisibleHint:" + isVisibleToUser);
        if(isVisibleToUser) {
            onUserVisible();
        }
    }

    protected void onUserVisible(){};

    /**
     * 初始化GoMVP
     */
    protected GoFragment() {
        GoMVP goMVP = new GoMVP.Builder()
                .view(this)
                .presenter(new LifecyclePresenter(this))
                .build();
        this.p_presenter = goMVP.getPresenter();
        p_presenter.addLifecycleListener(this);
    }

    @Override
    public void onCreate(Context context) {
        presenterAdapter = onPresenterAdapter(context);
        checkPresenterNull();
        if(presenterAdapter == null) {
            throw new RuntimeException("Adapter 还没有准备好！尝试在view create完成后就行调用");
        }
        GoLog.I("onActivityCreated,bindPresenterAdapter");
        p_presenter.bindPresenterAdapter(presenterAdapter);
    }

    protected  void request() {
        checkPresenterNull();
        p_presenter.request();
    }

    protected  void registerLoadingView(ExecuteStatusView listener) {
        checkPresenterNull();
        p_presenter.registerExecuteStatus(listener);
    }

    private void checkPresenterNull() {
        if(p_presenter == null) {
            throw new RuntimeException("presenter 还没有准备好！尝试在view create完成后就行调用");
        }
    }

    public LifecyclePresenter getPresenter() {
        return p_presenter;
    }

    protected abstract PresenterAdapter onPresenterAdapter(Context context);
}
