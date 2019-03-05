package com.example.lenovo.searchapp;

import android.app.Application;

import net.wequick.small.Small;

/**
 * Created by lenovo on 2019-03-04.
 */
public class SearchApplication extends Application {
    public SearchApplication(){
        Small.preSetUp(this);
    }
    /*@Override
    public void onCreate() {
        super.onCreate();
        Small.preSetUp(this);
    }*/
}
