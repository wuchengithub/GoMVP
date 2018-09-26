package com.wookii.gomvp.cache;

import android.content.Context;

import com.wookii.gomvp.GoLog;
import com.wookii.gomvp.respository.GoDataSource;
import com.wookii.gomvp.utils.SharedPUtil;

import static android.content.ContentValues.TAG;

/**
 * Created by wuchen on 2017/12/11.
 */

public class DefaultGoCache implements GoDataSource.GoCache<String, String> {

    private final Context context;

    public DefaultGoCache(Context context) {
        this.context = context;
    }

    @Override
    public void onAdd(String key, String value) {
        SharedPUtil.setParam(context, key, value);
        GoLog.D(TAG + "cache :" + "key:" + key + "....value:" + value);
    }

    @Override
    public String onGet(String key) {

        String s = SharedPUtil.getParam(context, key);
        GoLog.D(TAG + "cache out :" + "key:" + key + "....value:" + s);
        return s;
    }
}
