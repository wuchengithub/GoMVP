package com.wookii.gomvp;

import android.content.Context;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by wuchen on 2017/12/11.
 */

public class DefaultCacher  implements GoDataSource.Cacher<String, String>{

    private final Context context;

    public DefaultCacher(Context context) {
//        if (!(context instanceof Application)) {
//           throw new RuntimeException("context mast be Application! use getApplicationContext()") ;
//        }
        this.context = context;
    }

    @Override
    public void onAdd(String key, String value) {
        SharedPUtil.setParam(context, key, value);
        Log.e(TAG, "cache :" + "key:" + key + "....value:" + value);
    }

    @Override
    public String onGet(String key) {

        String s = SharedPUtil.getParam(context, key);
        Log.d(TAG, "cache out :" + "key:" + key + "....value:" + s);
        return s;
    }
}
