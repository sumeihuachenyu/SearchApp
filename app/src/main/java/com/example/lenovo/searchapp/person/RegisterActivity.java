package com.example.lenovo.searchapp.person;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.lenovo.searchapp.MyApplication;
import com.example.lenovo.searchapp.R;
import com.example.lenovo.searchapp.common.API;
import com.example.lenovo.searchapp.common.BaseActivity;
import com.example.lenovo.searchapp.person.model.User;
import com.example.lenovo.searchapp.utils.ActivityCollectorUtil;
import com.example.lenovo.searchapp.utils.TransformUtils;
import com.example.lenovo.searchapp.utils.Utils;
import com.example.lenovo.searchapp.utils.Xutils;
import com.orhanobut.logger.Logger;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by lenovo on 2019-03-06.
 * 注册页面
 */
@ContentView(R.layout.layout_register)
public class RegisterActivity extends BaseActivity {
    /** 手机号输入框 */
    @ViewInject(R.id.et_register_phone)
    private EditText mPhone;
    /** 用户名输入框*/
    @ViewInject(R.id.et_register_username)
    private EditText mUsername;
    /** 密码输入框 */
    @ViewInject(R.id.et_register_password)
    private EditText mPassword;
    /** 密码确认输入框 */
    @ViewInject(R.id.et_register_password_again)
    private EditText mPasswordAgain;
    private MyApplication myApplication;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        myApplication = MyApplication.getInstance();
        user = new User();
    }
    /**
     * 注册（点击注册按钮）
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.btn_register})
    private void register(View v) {
        //判断手机号是否为空
        if(mPhone.getText().toString().isEmpty()){
            Utils.showShortToast(this,"请输入手机号");
            return;
        }else{
            //判断手机号格式是否正确
            if (!TransformUtils.isMobile(mPhone.getText().toString())){
                Utils.showShortToast(this,"请输入格式正确的手机号");
                return;
            }
        }
        //判断用户名是否为空
        if(mUsername.getText().toString().isEmpty()){
            Utils.showShortToast(this,"请输入用户名");
            return;
        }else{
            //判断用户名是否包含非法字符：包含字母，数字，中文以及下划线，且下划线不能出现在首位和末尾
            if (!TransformUtils.isUsername(mUsername.getText().toString())){
                Utils.showShortToast(this,"用户名包含非法字符，请重新输入");
                return;
            }
        }
        //判读密码是否为空
        if(mPassword.getText().toString().isEmpty()){
            Utils.showShortToast(this,"请输入密码");
            return;
        }else{
            //判断密码的格式是否正确
            if(!TransformUtils.isPassword(mPassword.getText().toString())){
                Utils.showShortToast(this,"请输入6-20位包含字母和数字的密码");
                return;
            }
        }
        //判断再次输入密码是否为空
        if(mPasswordAgain.getText().toString().isEmpty()){
            Utils.showShortToast(this,"请再次输入密码");
            return;
        }else{
            //判断两次密码是否一致
            if(!mPassword.getText().toString().equals(mPasswordAgain.getText().toString())){
                Utils.showShortToast(this,"两次密码不一致，请重新输入");
                return;
            }
        }
        //构造签名生成算法所需要的数据
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mPhone.getText().toString());
        map.put("password", mPassword.getText().toString());
        map.put("username",mUsername.getText().toString());
        Log.i("用户数据",map.toString());
        //与服务器进行交互
        Xutils.getInstance(this).post(API.REGISTER_WITH_MOBILE, map, new Xutils.XCallBack() {
            @Override
            public void onResponse(String result) {
                Logger.d("在onResponse中的Result="+result);
                //如何将json字符串转换成JSON对象
                User data = Utils.parseJsonWithGson(result,User.class);
                Logger.d("注册页面的user="+data);
                //将获取到的用户数据存储到MyApplication中，在登录注册页面进行显示即可
                myApplication.setUser(data);
                ActivityCollectorUtil.finishAllActivity();
                Utils.start_Activity(RegisterActivity.this, LoginActivity.class);
            }
            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 跳转到登录（点击底部文字）
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.tv_register_login})
    private void goLogin(View v) {
        Utils.start_Activity(this, LoginActivity.class);
    }

}
