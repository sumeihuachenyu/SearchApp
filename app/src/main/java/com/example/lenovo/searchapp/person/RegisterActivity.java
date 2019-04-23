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
        if(mPhone.getText().toString().isEmpty()){
            Utils.showShortToast(this,"请输入手机号");
            return;
        }else{
            if (!TransformUtils.isMobile(mPhone.getText().toString())){
                Utils.showShortToast(this,"请输入格式正确的手机号");
                return;
            }
        }
        if(mUsername.getText().toString().isEmpty()){
            Utils.showShortToast(this,"请输入用户名");
            return;
        }else{
            if (!TransformUtils.isUsername(mUsername.getText().toString())){
                Utils.showShortToast(this,"用户名包含非法字符，请重新输入");
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
        if(mPasswordAgain.getText().toString().isEmpty()){
            Utils.showShortToast(this,"请再次输入密码");
            return;
        }else{
            if(!mPassword.getText().toString().equals(mPasswordAgain.getText().toString())){
                Utils.showShortToast(this,"两次密码不一致，请重新输入");
                return;
            }
        }

//        Utils.showShortToast(RegisterActivity.this,getString(R.string.register_success));
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mPhone.getText().toString());
        map.put("password", mPassword.getText().toString());
        map.put("username",mUsername.getText().toString());

        Log.i("用户数据",map.toString());
        //Utils.start_Activity(ReisterActivity.this, LoginActivity.class);//为了走通逻辑设置
        //与服务器进行交互
        Xutils.getInstance(this).post(API.REGISTER_WITH_MOBILE, map, new Xutils.XCallBack() {
            @Override
            public void onResponse(String result) {
                Logger.d("在onResponse中的Result="+result);
                //如何将json字符串转换成JSON对象
                User data = Utils.parseJsonWithGson(result,User.class);
                Logger.d("注册页面的user="+data);
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
     * 获取验证码（点击验证码文字）
     *
     * @param v 点击视图
     */
//    @Event(value = {R.id.tv_register_verify})
//    private void getVerify(View v) {
//        if (mPhone.getText().toString().isEmpty()){
//            Utils.showShortToast(this,"请输入手机号");
//            return;
//        }else{
//            if (!TransformUtils.isMobile(mPhone.getText().toString())){
//                Utils.showShortToast(this,"请输入格式正确的手机号");
//                return;
//            }
//        }
//
//        calcGetVerifyTime();
//        Map<String, String> map = new HashMap<>();
//        map.put("mobile", mPhone.getText().toString());
//        RequestParams params = new RequestParams(API.GET_VERIFY_MOBILE);
//        try {
//            params.addQueryStringParameter("sign", Utils.getSignature(map, Constants.SECRET));
//            params.addQueryStringParameter("mobile", mPhone.getText().toString());
//            x.http().get(params, new Callback.CommonCallback<String>() {
//                @Override
//                public void onSuccess(String result) {
//                    BaseResult baseResult = Utils.parseJsonWithGson(result, BaseResult.class);
//                    if (!(baseResult.getCode() == Constants.HTTP_OK_200)) {
//                        Utils.showShortToast(RegisterActivity.this, baseResult.getError());
//                    }
//                }
//
//                @Override
//                public void onError(Throwable ex, boolean isOnCallback) {
//                    Utils.showShortToast(RegisterActivity.this, getString(R.string.network_error));
//                }
//
//                @Override
//                public void onCancelled(CancelledException cex) {
//                    Utils.showShortToast(RegisterActivity.this, getString(R.string.network_error));
//                }
//
//                @Override
//                public void onFinished() {
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 计算再次获取验证码的时间
     *
     */
//    private void calcGetVerifyTime() {
//        mGetVerify.setEnabled(false);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 59; i >= 0; i--) {
//                    try {
//                        Thread.sleep(1000);
//                        mHandler.sendEmptyMessage(i);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
//    }

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
