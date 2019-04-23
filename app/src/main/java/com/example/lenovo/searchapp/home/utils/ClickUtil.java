package com.example.lenovo.searchapp.home.utils;

/**
 * 点击事件处理
 * Created by lenovo on 2019-03-24.
 */
public class ClickUtil {
    private static long lastClickTime;

    // 判断是否为快速点击事件
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500)
            return true;
        lastClickTime = time;
        return false;
    }
}
