package com.wookii.gomvp;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import static android.content.ContentValues.TAG;

/**
 * Created by wuchen on 2017/12/11.
 */

public class ActivityPresenter<T> extends BasePresenter<T> {
    private Context context;

    public ActivityPresenter(Context context) {
        this.context = context;
        onModel();
    }
    @Override
    public T getValue() {
        return value;
    }

    @Override
    public T getCache(Class<T> clazz) {
        String key = clazz.getName();
        String s = SharedPUtil.getParam(context, key);
        Log.d(TAG, "cache out :" + "key:" + key + "....value:" + s);
        Gson gson = new Gson();
        if(!TextUtils.isEmpty(s)) {
            return gson.fromJson(s, clazz);
        }
        return null;
    }

    @Override
    protected GoDataSource onModel() {
        return null;
    }
}
