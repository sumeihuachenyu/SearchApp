package com.example.lenovo.searchapp.person;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lenovo.searchapp.ProtocolActivity;
import com.example.lenovo.searchapp.R;
import com.example.lenovo.searchapp.utils.Utils;

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
public class LoginActivity extends AppCompatActivity {
    /** Log标记 */
    private final String TAG = "LoginActivity";
    /** 手机号输入框 */
    @ViewInject(R.id.et_login_phone)
    private EditText mPhone;
    /** 密码输入框 */
    @ViewInject(R.id.et_login_password)
    private EditText mPassword;
    @ViewInject(R.id.tv_agree_protocol)
    private TextView mProtocol;
    private String mUserName;
    private String mPortrait;
    private String mPhoneStr;
    private AlertDialog mLoadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // Utils.hideNavigationBar(this);
        x.view().inject(this);
        init();
    }

    private void init() {
        mLoadingDialog = new AlertDialog.Builder(this,R.style.dialog).create();//提示对话框
        mLoadingDialog.setCanceledOnTouchOutside(false);
        View content = LayoutInflater.from(this).inflate(R.layout.dialog_loading, null);//进行进度条的初始化
        mLoadingDialog.setView(content);
        //SpannableString可以直接作为TextView的显示文本，不同的是SpannableString可以通过使用其方法setSpan方法实现字符串各种形式风格的显示,重要的是可以指定设置的区间，
        // 也就是为字符串指定下标区间内的子字符串设置格式。
        SpannableString spanText=new SpannableString(getString(R.string.protocol));
        spanText.setSpan(new ClickableSpan() {

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.WHITE);       //设置文件颜色
                ds.setUnderlineText(true);      //设置下划线
            }
            @Override
            public void onClick(View view) {
                Utils.start_Activity(LoginActivity.this,ProtocolActivity.class);
            }
        }, spanText.length() - 8, spanText.length()-1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mProtocol.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
        mProtocol.setText(spanText);
        mProtocol.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件
    }

    /**
     * 登录（点击登录按钮）
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.btn_login})
    private void login(View v){
        if (mPhone.getText().toString().isEmpty()){
            Utils.showShortToast(this,"请输入账号");
            return;
        }
        if(mPassword.getText().toString().isEmpty()){
            Utils.showShortToast(this,"请输入密码");
            return;
        }
        Map<String,String> map = new HashMap<>();
        map.put("mobile",mPhone.getText().toString());
        map.put("password",mPassword.getText().toString());
        //不连服务器的时候，首先保证整个业务的流畅
        if(mPhone.getText().toString().equals("18209571211") && mPassword.getText().toString().equals("111111")){
            Utils.showShortToast(LoginActivity.this,getString(R.string.login_success));
        }
        //将数据传递到服务器端
//        Xutils.getInstance(this).post(API.LOGIN_WITH_MOBILE, map, new Xutils.XCallBack() {
//            @Override
//            public void onResponse(String result) {
//                LoginResult.UserInfo loginResult = Utils.parseJsonWithGson(result,LoginResult.UserInfo.class);
//                Utils.showShortToast(LoginActivity.this,getString(R.string.login_success));
//                mUserName = loginResult.getUserName();
//                mPortrait = loginResult.getPortrait();
//                loginByToken(loginResult.getToken());
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });
    }

    /**
     * 跳转到重置密码（点击重置文字）
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.tv_login_reset})
    private void resetPwd(View v){
        Utils.start_Activity(this,ResetActivity.class);
        //Utils.showShortToast(this,"重置密码");
    }
    /**
     * 跳转到注册页面
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.tv_login_register})
    private void register(View v){
        Utils.start_Activity(this,RegisterActivity.class);
       // Utils.showShortToast(this,"重置密码");
    }

}
