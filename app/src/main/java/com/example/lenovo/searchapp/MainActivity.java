package com.example.lenovo.searchapp;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.lenovo.searchapp.common.BaseActivity;
import com.example.lenovo.searchapp.frament.InputVertifyFragment;
import com.example.lenovo.searchapp.frament.inputPhoneFragment;

import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * 实现找回密码中
 * 输入手机号和填写验证码两个Fragment页面的跳转
 */
public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener{

    private SegmentedGroup mSegmentedGroup;//自定义控件组
    private RadioButton radioButtonOne, radioButtonTwo;//单选按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置一个Activity的显示界面，当前的Activity采用R.layout下的layout_top布局文件进行布局
        setContentView(R.layout.layout_top);
        initWidget();//初始化组件
        initEvent();//初始化事件

    }

    /**
     * 默认选择是哪个按钮被选中
     */
    private void initEvent() {
        radioButtonOne.setChecked(true);//默认选择第一个（案例展示碎片）
    }

    /**
     * 初始化组件
     */
    private void initWidget() {
        mSegmentedGroup = (SegmentedGroup) findViewById(R.id.mSegmentedGroup);//控件组
        radioButtonOne = (RadioButton) findViewById(R.id.radioButtonOne);//单选按钮一
        radioButtonTwo = (RadioButton) findViewById(R.id.radioButtonTwo);//单选按钮二

        mSegmentedGroup.setTintColor(Color.WHITE);//设置默认线条颜色及背景色
        mSegmentedGroup.setOnCheckedChangeListener(this);//绑定单选按钮选择监听

    }

    /**
     * 监听事件的处理
     * @param group
     * @param checkedId
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            //如果选择的是第一个按钮：输入手机号
            case R.id.radioButtonOne:
                FragmentManager fm = this.getSupportFragmentManager();//获得碎片管理器
                FragmentTransaction tr = fm.beginTransaction();//Transaction事务
                //将fragment碎片添加到对应的帧布局中
                tr.replace(R.id.foundFrameLayout, new inputPhoneFragment());//输入手机号页面碎片
                tr.commit();//提交
                break;
                //如果选择的是第二个按钮：输入验证码
            case R.id.radioButtonTwo:
                FragmentManager fm2 = this.getSupportFragmentManager();//获得碎片管理器
                FragmentTransaction tr2 = fm2.beginTransaction();//Transaction事务
                //将fragment碎片添加到对应的帧布局中
                tr2.replace(R.id.foundFrameLayout, new InputVertifyFragment());//输入验证码页面碎片
                tr2.commit();//提交
                break;
        }
    }
}