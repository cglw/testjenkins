package com.zhpan.bannerview.utils;

import android.content.res.Resources;
import android.util.Log;

public class BannerUtils {
    private static boolean debugMode = false;

    public static void setDebugMode(boolean isDebug) {
        debugMode = isDebug;
    }

    public static boolean isDebugMode() {
        return debugMode;
    }

    public static int dp2px(float dpValue) {
        return (int) (0.5f + (Resources.getSystem().getDisplayMetrics().density * dpValue));
    }

    public static void log(String tag, String msg) {
        if (debugMode) {
            Log.e(tag, msg);
        }
    }

    public static void log(String msg) {
        if (debugMode) {
            Log.e("BannerView", msg);
        }
    }

    public static int getRealPosition(boolean isCanLoop, int position, int pageSize) {
        return isCanLoop ? ((position - 1) + pageSize) % pageSize : (position + pageSize) % pageSize;
    }
}
