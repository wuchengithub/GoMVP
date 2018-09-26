package com.wookii.gomvp.demo.simple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.wookii.gomvp.GoLog;
import com.wookii.gomvp.annotation.DataSource;
import com.wookii.gomvp.annotation.GoBack;
import com.wookii.gomvp.annotation.GoError;
import com.wookii.gomvp.base.LifecyclePresenter;
import com.wookii.gomvp.demo.R;
import com.wookii.gomvp.demo.bean.MarketBean;
import com.wookii.gomvp.demo.bean.NoticeCountBean;
import com.wookii.gomvp.demo.repository.RepositoryInjection;
import com.wookii.gomvp.view.ExecuteStatusView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 可以直接继承GoActivity
 */
public class AnnoDemoActivity extends AppCompatActivity implements ExecuteStatusView {

    @BindView(R.id.button2)
    Button button;
    @BindView(R.id.button3)
    Button button3;

    /**
     * 注入Presenter,RepositoryInjection，
     * RepositoryInjection必须为DataSourceInjection的子类
     */
    @DataSource(RepositoryInjection.class)
    private LifecyclePresenter presenter;
    private MessageCountPresenter messagePresenterAdapter;


//    @Presenter()
//    private LifecyclePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_demo);

        messagePresenterAdapter = new MessageCountPresenter();
        messagePresenterAdapter.setStatus(0);

        presenter.registerExecuteStatus(this);
    }


    @GoBack
    public void hahaha(MarketBean bean) {
        GoLog.E("MarketBean is backing:" + bean.getValue().getMarketData().getAmountRtv());

    }


    @GoBack
    public void receiverData(NoticeCountBean bean) {
        GoLog.E("NoticeCountBean is backing:" + bean);
    }

    @GoError
    public void error(String errorMsg) {
        GoLog.E("error is backing:" + errorMsg);
    }

    @GoError
    public void error(NoticeCountBean bean, String errorMsg) {
        GoLog.E("NoticeCountBean error is backing:" + errorMsg);
    }

    @Override
    public void onExecuteBegin() {

    }

    @Override
    public void onExecuteFinish() {

    }

    @OnClick({R.id.button2, R.id.button3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button2:
                presenter.execute(new MarketPresenterAdapter());
                break;
            case R.id.button3:
                presenter.execute(messagePresenterAdapter);
                break;
        }
    }
}
