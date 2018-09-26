package com.wookii.gomvp.demo.simple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.wookii.gomvp.GoMVP;
import com.wookii.gomvp.base.GoView;
import com.wookii.gomvp.base.LifecyclePresenter;
import com.wookii.gomvp.demo.R;
import com.wookii.gomvp.demo.bean.MarketBean;
import com.wookii.gomvp.demo.repository.SimpleRepository;
import com.wookii.gomvp.view.ExecuteStatusView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Simple way
 */
public class SimpleDemoActivity extends AppCompatActivity implements GoView<MarketBean>, ExecuteStatusView {

    @BindView(R.id.button2)
    Button button;
    @BindView(R.id.button3)
    Button button3;

    private LifecyclePresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_demo);

        GoMVP mvp = new GoMVP.Builder()
                .view(this)
                .repository(new SimpleRepository())
                .presenter(new LifecyclePresenter(this))
                .build();

        presenter = mvp.getPresenter();

        presenter.bindPresenterAdapter(new MarketPresenterAdapter());
        presenter.registerExecuteStatus(this);

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
                presenter.request();
                break;
            case R.id.button3:
                break;
        }
    }

    @Override
    public void receiverData(LifecyclePresenter<MarketBean> marketBeanLifecyclePresenter) {

    }

    @Override
    public void showDataError(LifecyclePresenter<MarketBean> marketBeanLifecyclePresenter, String error) {

    }
}
