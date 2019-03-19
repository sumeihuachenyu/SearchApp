package com.example.lenovo.searchapp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.lenovo.searchapp.entity.UserInfoResult;
import com.example.lenovo.searchapp.person.LoginActivity;
import com.example.lenovo.searchapp.utils.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

/**
 * Created by lenovo on 2019-03-06.
 */
@ContentView(R.layout.layout_lunch1)
public class LaunchActivity extends FragmentActivity {
    private UserInfoResult.ResultBean mInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                login();
            }
        }).start();
    }

    private void login() {
        //String token = Utils.getValue(this,"token");
        //boolean isFirst = Utils.getBooleanValue(this,"first",true);
        //if (false){
            Utils.start_Activity(LaunchActivity.this,LoginActivity.class);
            //Utils.putBooleanValue(this,"first",false);
       // }else {
            //if (token.isEmpty()){
               // Utils.start_Activity(LaunchActivity.this,LoginActivity.class);
           // } else{
               // loginByToken(token);
           // }
      //  }
    }
}
