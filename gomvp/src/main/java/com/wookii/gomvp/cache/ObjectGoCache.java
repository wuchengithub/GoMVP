package com.wookii.gomvp.cache;

import android.content.Context;

import com.google.gson.Gson;
import com.wookii.gomvp.GoLog;
import com.wookii.gomvp.respository.GoDataSource;
import com.wookii.gomvp.utils.SharedPUtil;

/**
 * Created by wuchen on 2018/3/13.
 */

public class ObjectGoCache implements GoDataSource.GoCache<String, String> {
    private final Context context;

    public ObjectGoCache(Context context) {
        this.context = context;
    }

    @Override
    public void onAdd(String key, String value) {

    }

    public <T> T getObject(Class<T> clazz) {
        String s = onGet(clazz.getName());
        return new Gson().fromJson(s, clazz);
    }

    @Override
    public String onGet(String key) {
        String s = SharedPUtil.getParam(context, key);
        GoLog.D("cache out :" + "key:" + key + "....value:" + s);
        return s;
    }
}
