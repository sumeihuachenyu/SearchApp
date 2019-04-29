package com.example.lenovo.searchapp.person;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.lenovo.searchapp.MyApplication;
import com.example.lenovo.searchapp.R;
import com.example.lenovo.searchapp.common.API;
import com.example.lenovo.searchapp.common.BaseActivity;
import com.example.lenovo.searchapp.common.Constants;
import com.example.lenovo.searchapp.person.model.User;
import com.example.lenovo.searchapp.utils.TransformUtils;
import com.example.lenovo.searchapp.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2019-03-07.
 * 重置密码页面
 */
@ContentView(R.layout.activity_reset)
public class ResetActivity extends BaseActivity {
    /** Log标记 */
    private final String TAG = "ResetActivity";
    /** 密码输入框 */
    @ViewInject(R.id.et_register_password)
    private EditText password;
    /** 确认密码输入框 */
    @ViewInject(R.id.et_register_password_again)
    private EditText apassword;
    private MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        myApplication = MyApplication.getInstance();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 重置密码（点击重置按钮）
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.tv_reset_update})
    private void reset(View v){
        //判断密码是否为空
        if(password.getText().toString().isEmpty()){
            Utils.showShortToast(this,"请输入密码");
            return;
        }else{
            //判断密码格式是否正确
            if(!TransformUtils.isPassword(password.getText().toString())){
                Utils.showShortToast(this,"请输入6-20位包含字母和数字的密码");
                return;
            }
        }
        //判断再次输入密码是否为空
        if(apassword.getText().toString().isEmpty()){
            Utils.showShortToast(this,"请再次输入密码");
            return;
        }else{
            //判断两次密码是否一致
            if(!apassword.getText().toString().equals(password.getText().toString())){
                Utils.showShortToast(this,"两次密码不一致，请重新输入");
                return;
            }
        }

        //构造签名生成算法数据
        Map<String,String> map = new HashMap<>();
        map.put("mobile",myApplication.getData().getPhone());
        map.put("password",password.getText().toString());
        //http请求
        RequestParams params = new RequestParams(API.RESET_PASSWORD);
        try {
            params.addParameter("sign", Utils.getSignature(map, Constants.SECRET));
            params.addParameter("password",password.getText().toString());
            params.addParameter("mobile",myApplication.getData().getPhone());
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    try {
                        JSONObject baseResult = new JSONObject(result);
                        if (!(baseResult.getInt("status") == Constants.STATUS_OK)){
                            //需要提醒再次输入
                            password.setText("");
                            apassword.setText("");
                            Utils.showShortToast(x.app(), baseResult.getString("desc"));
                        }else if(baseResult.getInt("status") == Constants.STATUS_OK){
                            ///返回成功之后需要直接跳转到登录页面
                            User user = Utils.parseJsonWithGson(baseResult.getString("data"),User.class);
                            myApplication.setUser(user);
                            Utils.start_Activity(ResetActivity.this,LoginActivity.class);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Utils.showShortToast(ResetActivity.this, getString(R.string.network_error));
                }

                @Override
                public void onCancelled(CancelledException cex) {
                    Utils.showShortToast(ResetActivity.this, getString(R.string.network_error));
                }

                @Override
                public void onFinished() {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 退回登录（点击底部文字）
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.tv_reset_login})
    private void back(View v){
        Utils.start_Activity(ResetActivity.this,LoginActivity.class);
    }
    /**
     * 退回上一页
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.back_reset})
    private void back_reset(View v){
        this.finish();
    }

}
