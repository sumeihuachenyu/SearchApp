package com.example.lenovo.searchapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.wequick.small.Small;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Small.setUp(this, new Small.OnCompleteListener() {
            @Override
            public void onComplete() {
                //传值（只支持基本数据类型）
                //String uri=String.format("main?id=%s&title=%s",10,"hahaha");
                 Small.openUri("main", MainActivity.this);
                   }
                   });
                }

}
