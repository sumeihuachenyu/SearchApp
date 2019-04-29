package com.example.lenovo.searchapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.example.lenovo.searchapp.common.API;
import com.example.lenovo.searchapp.common.BaseActivity;
import com.example.lenovo.searchapp.person.LoginActivity;
import com.example.lenovo.searchapp.person.model.User;
import com.example.lenovo.searchapp.utils.Utils;
import com.example.lenovo.searchapp.utils.Xutils;
import com.orhanobut.logger.Logger;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2019-03-06.
 * 启动页
 */
@ContentView(R.layout.layout_lunch1)
public class LaunchActivity extends BaseActivity {
    private MyApplication myApplication;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        myApplication = MyApplication.getInstance();
    }


    /**
     * 点击开始
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.tv_launch_start})
    private void loginstart(View v){
        Logger.d("token="+myApplication.getToken());
        //如果是第一次登录
        if (myApplication.getToken() == null || myApplication.getToken().equals("") ){
            //需要登录，以前没有登陆过
            Logger.d("token="+myApplication.getUser());
            Utils.start_Activity(LaunchActivity.this,LoginActivity.class);
        }else if(myApplication.isExit() && myApplication.getUser() != null){
            //如果用户存在，主要针对退出操作做的
            myApplication.setExit(false);
            Utils.start_Activity(LaunchActivity.this,MainActivityTop.class);
        } else{
            //存在token，说明以前登录过
            Logger.d("token="+myApplication.getToken());
            Map<String,String> map = new HashMap<>();
            map.put("mobile","wuzhi");
            map.put("password","wuzhi");
            map.put("token",myApplication.getToken());
            Xutils.getInstance(LaunchActivity.this,LaunchActivity.this,false).post(API.LOGIN_WITH_MOBILE, map, new Xutils.XCallBack() {
                @Override
                public void onResponse(String result) {
                    User data = Utils.parseJsonWithGson(result,User.class);
                    Logger.d("登录页面的user="+user);
                    myApplication.setUser(data);
                    myApplication.setToken(data.getToken());
                    Logger.d("token="+myApplication.getToken());
                }
                @Override
                public void onFinished() {
                    Utils.showShortToast(LaunchActivity.this,getString(R.string.login_success));
                    Utils.start_Activity(LaunchActivity.this, MainActivityTop.class);
                    finish();
                }
            });
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String tag = intent.getStringExtra("EXIT_TAG");
        if (tag != null&& !TextUtils.isEmpty(tag)) {
            if ("SINGLETASKSINGLETASK".equals(tag)) {//退出程序
                finish();
            }
        }
    }
}
