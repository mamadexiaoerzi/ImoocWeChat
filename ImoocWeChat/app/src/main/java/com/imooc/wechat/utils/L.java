package com.imooc.wechat.utils;

import android.util.Log;

import com.imooc.wechat.BuildConfig;

/**
 * 日志管理类
 *
 * @author PanJ
 * @date 2019/7/29 09:18
 */
public class L {
    private static final String TAG = "PanJ";
    private static boolean sDebug = BuildConfig.DEBUG;

    // 打release包或给用户使用时，日志就不会出现
    public static void d(String msg, Object... args) {
        if (!sDebug) {
            return;
        }
        Log.d(TAG, String.format(msg, args));
    }
}
