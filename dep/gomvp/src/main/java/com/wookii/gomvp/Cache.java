package com.wookii.gomvp;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import static android.content.ContentValues.TAG;
import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by wuchen on 2017/12/13.
 */

public class Cache {
    public static <T> T getCache(Context context, Class<T> clazz) {
        checkNotNull(clazz);
        String key = clazz.getName();
        String s = SharedPUtil.getParam(context, key);
        Log.d(TAG, "cache out :" + "key:" + key + "....value:" + s);
        Gson gson = new Gson();
        return gson.fromJson(s, clazz);
    }

    public static <T> void setCache(Context context, T content, Class<T> clazz) {
        checkNotNull(clazz);
        String key = clazz.getName();
        Gson gson = new Gson();
        String value = gson.toJson(content, clazz);
        SharedPUtil.setParam(context, key, value);
        Log.d(TAG, "cache int :" + "key:" + key + "....value:" + value);
    }

}
