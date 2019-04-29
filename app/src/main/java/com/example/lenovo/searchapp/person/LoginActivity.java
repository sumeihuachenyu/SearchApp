package com.example.lenovo.searchapp.person;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import com.example.lenovo.searchapp.MainActivity;
import com.example.lenovo.searchapp.MainActivityTop;
import com.example.lenovo.searchapp.MyApplication;
import com.example.lenovo.searchapp.R;
import com.example.lenovo.searchapp.common.API;
import com.example.lenovo.searchapp.common.BaseActivity;
import com.example.lenovo.searchapp.person.model.User;
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
 * 登录页面
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
    /** 手机号输入框 */
    @ViewInject(R.id.et_login_phone)
    private EditText mPhone;
    /** 密码输入框 */
    @ViewInject(R.id.et_login_password)
    private EditText mPassword;
    private AlertDialog mLoadingDialog;
    private MyApplication myApplication;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        myApplication = MyApplication.getInstance();
        user = myApplication.getUser();
        //主要用于当注册成功之后无需用户自己输入用户名和密码
        Logger.d("登录：user="+user);
        if(user != null){
            mPhone.setText(user.getPhone());
            mPassword.setText(user.getPassword());
        }
    }

    /**
     * 登录（点击登录按钮）
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.btn_login})
    private void login(View v){
        //判读手机号是否为空
        if (mPhone.getText().toString().isEmpty()){
            Utils.showShortToast(this,"请输入手机号");
            return;
        }else{
            //判断手机号的格式是否正确
            if (!TransformUtils.isMobile(mPhone.getText().toString())){
                Utils.showShortToast(this,"请输入格式正确的手机号");
                return;
            }
        }
        //判断密码是否为空
        if(mPassword.getText().toString().isEmpty()){
            Utils.showShortToast(this,"请输入密码");
            return;
        }else{
            //判断密码格式是否正确
            if(!TransformUtils.isPassword(mPassword.getText().toString())){
                Utils.showShortToast(this,"请输入6-20位包含字母和数字的密码");
                return;
            }
        }
        //构造签名生成算法所需要的数据
        Map<String,String> map = new HashMap<>();
        map.put("mobile",mPhone.getText().toString());
        map.put("password",mPassword.getText().toString());
        Logger.d("token="+myApplication.getToken());
        if(myApplication.getToken() == null || myApplication.getToken().equals("")){
            map.put("token","无值");
        }else{
            map.put("token",myApplication.getToken());
        }
        //将数据传递到服务器端
        Xutils.getInstance(LoginActivity.this,LoginActivity.this,false).post(API.LOGIN_WITH_MOBILE, map, new Xutils.XCallBack() {
            @Override
            public void onResponse(String result) {
                User data = Utils.parseJsonWithGson(result,User.class);
                Logger.d("登录页面的user="+user);
                //将登陆者的信息存储到MyApplication中
                myApplication.setUser(data);
                //将token存储到MyApplication中
                myApplication.setToken(data.getToken());
                Logger.d("token="+myApplication.getToken());
                Utils.showShortToast(LoginActivity.this,getString(R.string.login_success));
                //进入到app中
                Utils.start_Activity(LoginActivity.this,MainActivityTop.class);
            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 跳转到重置密码（点击重置文字）
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.tv_login_reset})
    private void resetPwd(View v){
        Utils.start_Activity(this,MainActivity.class);
    }
    /**
     * 跳转到注册页面
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.tv_login_register})
    private void register(View v){
        Utils.start_Activity(this,RegisterActivity.class);
    }

}
