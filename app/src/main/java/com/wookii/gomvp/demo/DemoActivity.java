package com.wookii.gomvp.demo;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;

import com.wookii.gomvp.adapter.PresenterAdapter;
import com.wookii.gomvp.base.LifecyclePresenter;
import com.wookii.gomvp.demo.api.ApiServer;
import com.wookii.gomvp.demo.bean.MarketBean;
import com.wookii.gomvp.demo.components.BaseActivity;
import com.wookii.gomvp.demo.components.BasePresenterAdapter;
import com.wookii.gomvp.view.ILoadingView;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 如果要监听加载数据的过程，可以使用ILoadingView
 */
public class DemoActivity extends BaseActivity<MarketBean> implements ILoadingView {

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.editText2)
    MultiAutoCompleteTextView editText2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //可以注册ILoadingView，无需关注反注册
        registerLoadingView(this);
    }

    @Override
    protected PresenterAdapter onPresenterAdapter(Context context) {
        return new BasePresenterAdapter(context) {
            @Override
            protected Map<String, Object> onParams(Map<String, Object> params) {
                params.put("userPin", "");
                return params;
            }

            @Override
            protected String onMethod() {
                return ApiServer.Method.METHOD_MARKET_DATA;
            }
        };
    }


    @Override
    public void onLoading() {
        //custom loading view showing
    }
    @Override
    public void onLoadingFinish() {
    }

    @Override
    public void receiverData(LifecyclePresenter<MarketBean> p) {

        editText2.setText(p.getResponseString());
    }

    @Override
    public void showDataError(LifecyclePresenter<MarketBean> p, String error) {

    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        //执行数据请求
        request();
    }
}
