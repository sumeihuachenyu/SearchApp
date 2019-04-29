package com.example.lenovo.searchapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.lenovo.searchapp.R;
import com.example.lenovo.searchapp.home.model.SearchAndPerson;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lenovo on 2019-03-06.
 * 工具类
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
        activity.overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);

    }

    /**
     * 打开Activity
     *
     * @param activity
     * @param cls
     */
    public static void start_Activity(Activity activity, Class<?> cls, String paramName,String paramValue) {
        Intent intent = new Intent(activity, cls); //声明一个Intent对象，构造函数参数为第一个页面与第二个页面
        intent.putExtra(paramName, paramValue);//给Intent对象绑定数据，类比HashMap 的键-值对形式
        activity.startActivity(intent);//跳转页面
        //右往左推出效果
        activity.overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);

    }

    /**
     * 打开Activity
     *
     * @param activity
     * @param cls
     */
    public static void start_Activity(Activity activity, Class<?> cls, String paramName, SearchAndPerson paramValue) {
        Intent intent = new Intent(activity, cls); //声明一个Intent对象，构造函数参数为第一个页面与第二个页面
        intent.putExtra(paramName, paramValue.toString());//给Intent对象绑定数据，类比HashMap 的键-值对形式
        activity.startActivity(intent);//跳转页面
        //右往左推出效果
        activity.overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);

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
        activity.overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);

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
        activity.overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);

    }
    /**
     * 签名生成算法
     * @param params 请求参数集，所有参数必须已转换为字符串类型
     * @param secret 签名密钥
     * @return 签名
     * @throws IOException
     */
    public static String getSignature(Map<String,String> params, String secret) throws IOException {
        // 先将参数以其参数名的字典序升序进行排序
        Map<String, String> sortedParams = new TreeMap<String, String>(params);
        Set<Map.Entry<String, String>> entrys = sortedParams.entrySet();

        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder basestring = new StringBuilder();
        for (Map.Entry<String, String> param : entrys) {
            //忽略签名字段
            if (param.getKey().equalsIgnoreCase("sign")) {
                continue;
            }
            basestring.append(param.getKey()).append("=").append(param.getValue()).append("&");
        }
        if (params.size() > 0) {
            basestring.deleteCharAt(basestring.length() - 1);
        }
        basestring.append(secret);
        Logger.d("sign "+basestring);
        // 使用MD5对待签名串求签
        byte[] bytes = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            bytes = md5.digest(basestring.toString().getBytes("UTF-8"));
        } catch (GeneralSecurityException ex) {
            throw new IOException(ex);
        }

        // 将MD5输出的二进制结果转换为小写的十六进制
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex);
        }
        return sign.toString();
    }

    /**
     * 通过gson将json字符串转换成实体
     *
     * @param jsonData json字符串
     * @param type 实体类型
     * @return 实体
     */
    public static <T> T parseJsonWithGson(String jsonData, Class<T> type) {
        Gson gson = new Gson();
        T result = gson.fromJson(jsonData, type);
        return result;
    }

    private static float sDensity = 0;
    /**
     * DP转换为像素
     *
     * @param context
     * @param nDip
     * @return
     */
    public static int dipToPixel(Context context, int nDip) {
        if (sDensity == 0) {
            final WindowManager wm = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);
            sDensity = dm.density;
        }
        return (int) (sDensity * nDip);
    }

    /**
     * 获取SharedPreference 值
     *
     * @param context
     * @param key
     * @return
     */
    public static final String getValue(Context context, String key) {
        return getSharedPreference(context).getString(key, "");
    }

    public static final String getValue(Context context, String key, String def) {
        return getSharedPreference(context).getString(key, def);
    }

    public static final float getFloatValue(Context context, String key) {
        return getSharedPreference(context).getFloat(key, 0.00f);
    }

    public static final Boolean getBooleanValue(Context context, String key) {
        return getSharedPreference(context).getBoolean(key, false);
    }

    public static final void putBooleanValue(Context context, String key,
                                             boolean bl) {
        SharedPreferences.Editor edit = getSharedPreference(context).edit();
        edit.putBoolean(key, bl);
        edit.commit();
    }

    public static final int getIntValue(Context context, String key) {
        return getSharedPreference(context).getInt(key, 0);
    }

    public static final long getLongValue(Context context, String key,
                                          long default_data) {
        return getSharedPreference(context).getLong(key, default_data);
    }

    public static final boolean putLongValue(Context context, String key,
                                             Long value) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    public static final Boolean hasValue(Context context, String key) {
        return getSharedPreference(context).contains(key);
    }

    private static final SharedPreferences getSharedPreference(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * 判断字符串是否包含中文
     *
     * @param str
     * @return
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        return m.find();
    }/**
     * 判断字符串是否包含字母
     *
     * @param str
     * @return
     */
    public static boolean isContainLetters(String str) {
        for (char i = 'A'; i <= 'Z'; i++) {
            if (str.contains(String.valueOf(i))) {
                return true;
            }
        }
        for (char i = 'a'; i <= 'z'; i++) {
            if (str.contains(String.valueOf(i))) {
                return true;
            }
        }
        return false;
    }
}
