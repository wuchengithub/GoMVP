package com.wookii.gomvp.demo.simple;

import android.content.Context;
import android.util.Pair;

import com.wookii.gomvp.adapter.InterceptGoBack;
import com.wookii.gomvp.adapter.PresenterAdapter;
import com.wookii.gomvp.demo.api.ApiServer;
import com.wookii.gomvp.demo.bean.HttpResult;
import com.wookii.gomvp.demo.bean.NoticeCountBean;
import com.wookii.gomvp.utils.RetrofitConverter;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.Retrofit;

class MessageCountPresenter extends PresenterAdapter implements InterceptGoBack<NoticeCountBean>{
    private int status;

    @Override
    public Class targetBeanType() {
        return NoticeCountBean.class;
    }

    @Override
    public String action() {
        return null;
    }

    @Override
    public Observable onCreateObservable(Context context, RetrofitConverter retrofitConverter) {
        Retrofit retrofit = retrofitConverter.createRetrofit();
        ApiServer apiServer = retrofit.create(ApiServer.class);
        HashMap<String, Object> map = new HashMap<>();
        map.put("status",status);
        Observable<HttpResult<NoticeCountBean>> noticeCountBean = apiServer.getNoticeCountBean(map);
        return noticeCountBean;
    }

    @Override
    public Pair onSuccessCodePair() {
        return new Pair("success","true");
    }

    @Override
    public String onErrorMessageKey() {
        return "message";
    }

    @Override
    public boolean intercept(NoticeCountBean bean) {
        //NoticeCountBean被拦截了
        bean.setTotalElements(1000);
        return false;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
