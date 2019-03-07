package com.example.lenovo.searchapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by lenovo on 2019-03-06.
 */
public class Utils {
    public static void showLongToast(Context context, String pMsg) {
        Toast.makeText(context, pMsg, Toast.LENGTH_LONG).show();
    }

    public static void showShortToast(Context context, String pMsg) {
        Toast.makeText(context, pMsg, Toast.LENGTH_SHORT).show();
    }
    /**
     * 打开Activity
     *
     * @param activity
     * @param cls
     */
    public static void start_Activity(Activity activity, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        activity.startActivity(intent);
        //右往左推出效果
//        activity.overridePendingTransition(R.anim.push_left_in,
//                R.anim.push_left_out);

    }

    /**
     * 打开Activity
     *
     * @param activity
     * @param intent
     */
    public static void start_Activity(Activity activity,Intent intent) {
        activity.startActivity(intent);
        //右往左推出效果

    }


    /**
     * 打开Activity
     *
     * @param activity
     * @param intent
     */
    public static void startAcitivityForResult(Activity activity, Intent intent, int code) {
        activity.startActivityForResult(intent,code);
        //右往左推出效果

    }
}
