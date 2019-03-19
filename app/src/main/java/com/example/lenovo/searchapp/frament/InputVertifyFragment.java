package com.example.lenovo.searchapp.frament;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lenovo.searchapp.MyApplication;
import com.example.lenovo.searchapp.R;
import com.example.lenovo.searchapp.person.LoginActivity;
import com.example.lenovo.searchapp.person.ResetActivity;
import com.example.lenovo.searchapp.utils.Utils;
import com.orhanobut.logger.Logger;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by lenovo on 2019-03-15.
 */
@ContentView(R.layout.tab_inputvedify)
public class InputVertifyFragment extends Fragment {
    /**输入的验证码*/
    @ViewInject(R.id.et_reset_phone)
    private EditText editText;
    /**点击进行验证*/
    @ViewInject(R.id.tv_reset_verify)
    private TextView textView;
    private Activity mContext;
    private MyApplication myApplication;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.tab_inputvedify, container, false);
        View view = x.view().inject(this,inflater,container);
        //editText = (EditText) view.findViewById(R.id.et_reset_phone);
        myApplication = MyApplication.getInstance();
        mContext = this.getActivity();
        init();
        return view;
    }
    public void init(){
        Logger.d("myapplication="+myApplication.getData());
        if(!myApplication.getData().equals("")){
            editText.setText(myApplication.getData());
        }else{
            Utils.showShortToast(mContext,"请先输入手机号获取验证码");
        }
    }
    @Event(value = {R.id.tv_reset_verify})
    private void click(View v){
        //Utils.start_Activity(mContext,LoginActivity.class);
        //需要将刚刚传递过来的验证码进行验证
        if("1111".equals(editText.getText().toString())){
            //进入密码修改页面
            Utils.start_Activity(mContext,ResetActivity.class);
        }else{
            Utils.showLongToast(mContext,"验证码错误，请重新输入手机号进行获取");
        }
    }
    /**
     * 退回登录（点击底部文字）
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.tv_reset_login2})
    private void backlogin(View v){
        Utils.start_Activity(mContext,LoginActivity.class);
    }
}
