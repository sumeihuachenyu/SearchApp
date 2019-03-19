package com.example.lenovo.searchapp.person;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.lenovo.searchapp.MainActivity;
import com.example.lenovo.searchapp.MainActivityTop;
import com.example.lenovo.searchapp.MyApplication;
import com.example.lenovo.searchapp.R;
import com.example.lenovo.searchapp.utils.TransformUtils;
import com.example.lenovo.searchapp.utils.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by lenovo on 2019-03-06.
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // Utils.hideNavigationBar(this);
        x.view().inject(this);
        myApplication = MyApplication.getInstance();
        myApplication.setData("");
        init();
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
        View content = LayoutInflater.from(this).inflate(R.layout.dialog_loading, null);//进行进度条的初始化
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
//        Map<String,String> map = new HashMap<>();
//        map.put("mobile",mPhone.getText().toString());
//        map.put("password",mPassword.getText().toString());
//        //不连服务器的时候，首先保证整个业务的流畅
        if(mPhone.getText().toString().equals("18209571211") && mPassword.getText().toString().equals("111111sm")){
            Utils.showShortToast(LoginActivity.this,getString(R.string.login_success));
         new Thread(new Runnable() {
           @Override
           public void run() {
               login();
           }
       }).start();
            //startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }

//        String verify = "1111";
//        Intent smsIntent=new Intent(Intent.ACTION_SENDTO,
//                Uri.parse("sms:"+mPhone.getText().toString()));
//        smsIntent.putExtra("sms_body", "您的验证码为"+verify+",此验证码30分钟内有效");
//        try {
//            this.startActivity(smsIntent);
//        } catch(ActivityNotFoundException exception) {
//            Toast.makeText(this, "no activity", Toast.LENGTH_SHORT).show();
//        }

        //将数据传递到服务器端
//        Xutils.getInstance(this).post(API.LOGIN_WITH_MOBILE, map, new Xutils.XCallBack() {
//            @Override
//            public void onResponse(String result) {
//                LoginResult.UserInfo loginResult = Utils.parseJsonWithGson(result,LoginResult.UserInfo.class);
//                Utils.showShortToast(LoginActivity.this,getString(R.string.login_success));
//                mUserName = loginResult.getUserName();
//                mPortrait = loginResult.getPortrait();
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });
    }

    private void login() {
        //String token = Utils.getValue(this,"token");
        //boolean isFirst = Utils.getBooleanValue(this,"first",true);
        //if (false){
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
