package com.wookii.gomvp.demo.components;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wookii.gomvp.GoLog;
import com.wookii.gomvp.utils.RetrofitConverter;
import com.wookii.gomvp.adapter.PresenterAdapter;
import com.wookii.gomvp.demo.api.ApiServer;
import com.wookii.gomvp.demo.bean.HttpResult;
import com.wookii.gomvp.demo.bean.SecretKeyBean;
import com.wookii.gomvp.demo.utils.Base64;
import com.wookii.gomvp.demo.utils.SignUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public abstract class BasePresenterAdapter extends PresenterAdapter{
    public Context context;

    public BasePresenterAdapter (Context context) {
        this.context = context;
    }
    protected abstract Map<String, Object> onParams(Map<String, Object> params);
    @Override
    public Observable onCreateObservable(RetrofitConverter retrofit) {
        ApiServer apiServer = retrofit.create(ApiServer.class);
        //准备sko，获取secret key
        Map<String, Object> skm = new HashMap();
        skm.put("appKey", "b557eae9139e2f414f9421fa8a2ea91b");
        HashMap<String, String> signMap = getSignMap(skm, ApiServer.Method.METHOD_GET_SECRETKEY);
        //转一下value的类型
        HashMap<String, Object> newMap = new HashMap(signMap);
        Observable<HttpResult<SecretKeyBean>> sko = apiServer.getSecretKey(newMap);

        return sko.subscribeOn(Schedulers.io()) //IO sk
                .observeOn(AndroidSchedulers.mainThread())//主线程处理sk结果
                .observeOn(Schedulers.io())
                .filter(secretKeyBeanHttpResult -> {
                    boolean isSuccess = secretKeyBeanHttpResult.isSuccess();
                    if (!isSuccess) {
                        //token 失效
                        if(secretKeyBeanHttpResult.getCode().equals(ApiServer.Code.CODE_EXPIRY_TOKEN)) {
                            sendLogoutBroadcast();
                        }
                        String message = secretKeyBeanHttpResult.getMessage();
                        GoLog.E(secretKeyBeanHttpResult.getCode() + message + ":" + secretKeyBeanHttpResult.getClass().getSimpleName());
                        return false;
                    }
                    return true;
                })
                .flatMap(secretKeyBeanHttpResult -> {
                    String sk = secretKeyBeanHttpResult.getResult().getValue();
                    GoLog.E("SK......................:" + sk);
                    String skString = new String(Base64.decode(sk));
                    return buildRequestObservable(skString, apiServer);
                });
    }

    @NonNull
    private HashMap<String, String> getSignMap(Map<String, Object> skm, String method) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String requestJsonParam = gson.toJson(skm);
        GoLog.E("..............." + requestJsonParam);
        //加密
        byte[] byteArr = android.util.Base64.encode(requestJsonParam.getBytes(), android.util.Base64.DEFAULT);
        //先排序加密
        HashMap<String,String> signMap = new HashMap();
        signMap.put("app_key", "b557eae9139e2f414f9421fa8a2ea91b");
        signMap.put("access_token", "d3d5bfaaf546f87a89440d3ac082675c");
        signMap.put("method", method);
        signMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
        signMap.put("v", "1.0");
        signMap.put("param_json", new String(byteArr));
        return signMap;
    }


    private Observable<HttpResult<Object>> buildRequestObservable(String sk, ApiServer apiServer) {
        Map<String, Object> stringObjectMap = onParams(new HashMap<>());
        stringObjectMap.put("tenantId","1");
        stringObjectMap.put("userPin","bjferx");
        HashMap<String, String> signMap = getSignMap(stringObjectMap, onMethod());
        if(sk != null) {
            String sign = SignUtils.buildSign(signMap, sk, false, new ArrayList());
            signMap.put("sign", sign);
        }
        HashMap<String, Object> newMap = new HashMap<>(signMap);
        return apiServer.getMethods(newMap);
    }

    private void sendLogoutBroadcast() {
        Intent intent = new Intent("action_logout");
        LocalBroadcastManager.getInstance(context)
                .sendBroadcast(intent);
    }

    protected abstract String onMethod();

    public Context getContext() {
        return context;
    }

    public void setSecretKey(String secretKey) {
        String secretKey1 = secretKey;
    }
}
