package com.example.lenovo.searchapp.person;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
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
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
    /** Log标记 */
    private final String TAG = "LoginActivity";
    /** 手机号输入框 */
    @ViewInject(R.id.et_login_phone)
    private EditText mPhone;
    /** 密码输入框 */
    @ViewInject(R.id.et_login_password)
    private EditText mPassword;
    private String mUserName;
    private String mPortrait;
    private String mPhoneStr;
    private AlertDialog mLoadingDialog;
    private Handler handler;
    private MyApplication myApplication;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // Utils.hideNavigationBar(this);
        x.view().inject(this);
        myApplication = MyApplication.getInstance();
        user = myApplication.getUser();
        Logger.d("登录：user="+user);
        if(user != null){
            mPhone.setText(user.getPhone());
            mPassword.setText(user.getPassword());
        }
        //init();
    }

    private void init() {
        mLoadingDialog = new AlertDialog.Builder(this,R.style.dialog).create();//提示对话框,当点击登录之后出现
        mLoadingDialog.setCanceledOnTouchOutside(false);//dialog弹出后会点击屏幕，dialog不消失；点击物理返回键dialog消失
        /**
         * Inflate()作用就是将xml定义的一个布局找出来，但仅仅是找出来而且隐藏的，没有找到的同时并显示功能。而setContentView()将布局设置成当前屏幕即Activity的内容，可以直接显示出来。
         * SetContentView()一旦调用, layout就会立刻显示UI；而inflate只会把Layout形成一个以view类实现成的对象。有需要时再用setContentView(view)显示出来。
         * 注：Inflate()或可理解为“隐性膨胀”，隐性摆放在view里，inflate()前只是获得控件，但没有大小没有在View里占据空间，inflate()后有一定大小，只是出于隐藏状态。
         * 一般在activity中通过setContentView()将界面显示出来，但是如果在非activity中如何对控件布局设置操作了，这需LayoutInflater动态加载。
         */
        View content = LayoutInflater.from(this).inflate(R.layout.pop_item, null);//进行进度条的初始化
        mLoadingDialog.setView(content);//如果需要自定义对话框界面
        //SpannableString可以直接作为TextView的显示文本，不同的是SpannableString可以通过使用其方法setSpan方法实现字符串各种形式风格的显示,重要的是可以指定设置的区间，
        // 也就是为字符串指定下标区间内的子字符串设置格式。
    }

    /**
     * 登录（点击登录按钮）
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.btn_login})
    private void login(View v){
        if (mPhone.getText().toString().isEmpty()){
            Utils.showShortToast(this,"请输入手机号");
            return;
        }else{
            if (!TransformUtils.isMobile(mPhone.getText().toString())){
                Utils.showShortToast(this,"请输入格式正确的手机号");
                return;
            }
        }
        if(mPassword.getText().toString().isEmpty()){
            Utils.showShortToast(this,"请输入密码");
            return;
        }else{
            if(!TransformUtils.isPassword(mPassword.getText().toString())){
                Utils.showShortToast(this,"请输入6-20位包含字母和数字的密码");
                return;
            }
        }
        Map<String,String> map = new HashMap<>();
        map.put("mobile",mPhone.getText().toString());
        map.put("password",mPassword.getText().toString());
        Logger.d("token="+myApplication.getToken());
        if(myApplication.getToken() == null || myApplication.getToken().equals("")){
            map.put("token","无值");
        }else{
            map.put("token",myApplication.getToken());
        }
        //map.put("canshu","你好");

        //将数据传递到服务器端
        Xutils.getInstance(LoginActivity.this,LoginActivity.this,false).post(API.LOGIN_WITH_MOBILE, map, new Xutils.XCallBack() {
            @Override
            public void onResponse(String result) {
               // LoginResult.UserInfo loginResult = Utils.parseJsonWithGson(result,LoginResult.UserInfo.class);
                User data = Utils.parseJsonWithGson(result,User.class);
                Logger.d("登录页面的user="+user);
                myApplication.setUser(data);
                myApplication.setToken(data.getToken());
                Logger.d("token="+myApplication.getToken());
                Utils.showShortToast(LoginActivity.this,getString(R.string.login_success));
                Utils.start_Activity(LoginActivity.this,MainActivityTop.class);
            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void login() {
        //String token = Utils.getValue(this,"token");
        //boolean isFirst = Utils.getBooleanValue(this,"first",true);
        //if (false){
        myApplication.setToken("111111");
        Utils.start_Activity(LoginActivity.this,MainActivityTop.class);
        //Utils.putBooleanValue(this,"first",false);
        // }else {
        //if (token.isEmpty()){
        // Utils.start_Activity(LaunchActivity.this,LoginActivity.class);
        // } else{
        // loginByToken(token);
        // }
        //  }
    }
    /**
     * 跳转到重置密码（点击重置文字）
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.tv_login_reset})
    private void resetPwd(View v){
        Utils.start_Activity(this,MainActivity.class);
        //Utils.showShortToast(this,"重置密码");
    }
    /**
     * 跳转到注册页面
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.tv_login_register})
    private void register(View v){
        //Utils.start_Activity(this,RegisterActivity.class);
       // Utils.showShortToast(this,"重置密码");
        Utils.start_Activity(this,RegisterActivity.class);
    }

}
