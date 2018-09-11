package com.wookii.gomvp.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mashikun on 2017/11/16.
 */

public class SharedPUtil {
    private static final String SP_FILE_NAME = "GoMVP";
    public static void setParam(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(SP_FILE_NAME, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getParam(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(SP_FILE_NAME, 0);
        return sp.getString(key, "");
    }

    public static boolean cleanAll(Context context) {
        try {
            SharedPreferences ex = context.getSharedPreferences(SP_FILE_NAME, 0);
            SharedPreferences.Editor editor = ex.edit();
            editor.clear();
            editor.apply();
            return true;
        } catch (Exception var3) {
            return false;
        }
    }
}
