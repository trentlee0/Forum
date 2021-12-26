package com.example.forum.util;

import android.util.Log;

public class LogUtil {
    public static final String TAG = "ForumTag";

    public static void log(Object msg) {
        Log.i(TAG, String.valueOf(msg));
    }

    public static void d(Object msg) {
        Log.d(TAG, String.valueOf(msg));
    }

    public static void e(Object msg) {
        Log.e(TAG, String.valueOf(msg));
    }

    public static void v(Object msg) {
        Log.v(TAG, String.valueOf(msg));
    }

    public static void w(Object msg) {
        Log.w(TAG, String.valueOf(msg));
    }
}
