package com.example.lenovo.searchapp;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.lenovo.searchapp.frament.InputVertifyFragment;
import com.example.lenovo.searchapp.frament.inputPhoneFragment;

import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * 实现找回密码中输入手机号和填写验证码两个Fragment页面的跳转
 */
public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    private SegmentedGroup mSegmentedGroup;
    private RadioButton radioButtonOne, radioButtonTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_top);

        initWidget();//初始化组件
        initData();//初始化数据
        initEvent();//初始化是事件

    }

    private void initEvent() {
        radioButtonOne.setChecked(true);//默认选择第一个（案例展示碎片）
    }

    private void initData() {
    }

    private void initWidget() {
        mSegmentedGroup = (SegmentedGroup) findViewById(R.id.mSegmentedGroup);//控件组
        radioButtonOne = (RadioButton) findViewById(R.id.radioButtonOne);//单选按钮一
        radioButtonTwo = (RadioButton) findViewById(R.id.radioButtonTwo);//单选按钮二

        mSegmentedGroup.setTintColor(Color.WHITE);//设置默认线条颜色及背景色
        mSegmentedGroup.setOnCheckedChangeListener(this);//绑定单选按钮选择监听

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radioButtonOne:
                FragmentManager fm = this.getSupportFragmentManager();//获得碎片管理器
                FragmentTransaction tr = fm.beginTransaction();//Transaction事物
                //将fragment碎片添加到对应的帧布局中
                tr.replace(R.id.foundFrameLayout, new inputPhoneFragment());//案例展示碎片
                tr.commit();//提交
                break;
            case R.id.radioButtonTwo:
                FragmentManager fm2 = this.getSupportFragmentManager();
                FragmentTransaction tr2 = fm2.beginTransaction();
                tr2.replace(R.id.foundFrameLayout, new InputVertifyFragment());//导师咨询碎片
                tr2.commit();
                break;
        }
    }
}