package com.example.lenovo.searchapp;

import android.app.Application;
import org.xutils.x;
/**
 * Created by lenovo on 2019-03-06.
 */
public class MyApplication extends Application {
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true); //是否输出debug日志，开启debug会影响性能。
    }

}
