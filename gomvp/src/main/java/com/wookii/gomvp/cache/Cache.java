package com.wookii.gomvp.cache;

import android.content.Context;

import com.google.gson.Gson;
import com.wookii.gomvp.GoLog;
import com.wookii.gomvp.utils.SharedPUtil;

import static android.content.ContentValues.TAG;
import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by wuchen on 2017/12/13.
 */

class Cache {
    public static <T> T getCache(Context context, Class<T> clazz) {
        checkNotNull(clazz);
        String key = clazz.getName();
        String s = SharedPUtil.getParam(context, key);
        GoLog.D(TAG + "cache out :" + "key:" + key + "....value:" + s);
        Gson gson = new Gson();
        return gson.fromJson(s, clazz);
    }

    public static <T> void setCache(Context context, T content, Class<T> clazz) {
        checkNotNull(clazz);
        String key = clazz.getName();
        Gson gson = new Gson();
        String value = gson.toJson(content, clazz);
        SharedPUtil.setParam(context, key, value);
        GoLog.D(TAG + "cache int :" + "key:" + key + "....value:" + value);
    }

}
