package com.jianping.lee.dailynews.utils;

import android.util.Log;

import com.jianping.lee.dailynews.base.ConstConfig;

/**
 * @fileName: LogUtils
 * @Author: Li Jianping
 * @Date: 2016/6/3 12:27
 * @Description: log管理类
 */
public class LogUtils {

    public static boolean isDebug = ConstConfig.debugMode;
    private static final String TAG = "dailyNews";
    private LogUtils(){

    }

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (isDebug)
            Log.i(TAG, msg);
    }

    public static void d(String msg) {
        if (isDebug)
            Log.d(TAG, msg);
    }

    public static void e(String msg) {
        if (isDebug)
            Log.e(TAG, msg);
    }

    public static void v(String msg) {
        if (isDebug)
            Log.v(TAG, msg);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }
}
