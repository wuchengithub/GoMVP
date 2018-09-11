package com.wookii.gomvp;

import android.util.Log;


/**
 * Common Utils for the application
 */
public class GoLog {

    private static final boolean sLogShow = BuildConfig.DEBUG;
    public static String sLogTag = "LogUtils";

    private static final String TAG = "LogUtils";

    public static void V(String msg) {
        if (sLogShow && msg != null) {
            Log.v(sLogTag, msg);
        }
    }

    public static void V(String msg, Throwable e) {
        if (sLogShow && msg != null) {
            Log.v(sLogTag, msg, e);
        }
    }

    public static void D(String msg) {
        if (sLogShow && msg != null) {
            Log.d(sLogTag, msg);
        }
    }

    public static void D(String msg, Throwable e) {
        if (sLogShow && msg != null) {
            Log.d(sLogTag, msg, e);
        }
    }

    public static void I(String msg) {
        if (sLogShow && msg != null) {
            Log.i(sLogTag, msg);
        }
    }

    public static void I(String msg, Throwable e) {
        if (sLogShow && msg != null) {
            Log.i(sLogTag, msg, e);
        }
    }

    public static void W(String msg) {
        if (sLogShow && msg != null) {
            Log.w(sLogTag, msg);
        }
    }

    public static void W(String msg, Throwable e) {
        if (sLogShow && msg != null) {
            Log.w(sLogTag, msg, e);
        }
    }

    public static void E(String msg) {
        if (sLogShow && msg != null) {
            Log.e(sLogTag, msg);
        }
    }

    public static void E(String msg, Throwable e) {
        if (sLogShow && msg != null) {
            Log.e(sLogTag, msg, e);
        }
    }


}