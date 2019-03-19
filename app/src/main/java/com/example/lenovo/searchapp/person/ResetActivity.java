package com.example.lenovo.searchapp.person;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.lenovo.searchapp.R;
import com.example.lenovo.searchapp.common.BaseActivity;
import com.example.lenovo.searchapp.utils.TransformUtils;
import com.example.lenovo.searchapp.utils.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by lenovo on 2019-03-07.
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Utils.hideNavigationBar(this);
        x.view().inject(this);
    }

    /**
     * 重置密码（点击重置按钮）
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.tv_reset_update})
    private void reset(View v){
        if(password.getText().toString().isEmpty()){
            Utils.showShortToast(this,"请输入密码");
            return;
        }else{
            if(!TransformUtils.isPassword(password.getText().toString())){
                Utils.showShortToast(this,"请输入6-20位包含字母和数字的密码");
                return;
            }
        }
        if(apassword.getText().toString().isEmpty()){
            Utils.showShortToast(this,"请再次输入密码");
            return;
        }else{
            if(!apassword.getText().toString().equals(password.getText().toString())){
                Utils.showShortToast(this,"两次密码不一致，请重新输入");
                return;
            }
        }
        Utils.showShortToast(ResetActivity.this,"密码修改成功");
        Utils.start_Activity(ResetActivity.this,LoginActivity.class);
                //update();


//        Map<String,String> map = new HashMap<>();
//        map.put("mobile",mPhone.getText().toString());
//        map.put("password",mPassword.getText().toString());
//        map.put("verify",mVerify.getText().toString());
        //Utils.start_Activity(ResetActivity.this,LoginActivity.class);//走通逻辑而写
//        RequestParams params = new RequestParams(API.RESET_PASSWORD);
//        try {
//            params.addParameter("sign", Utils.getSignature(map, Constants.SECRET));
//            params.addParameter("mobile",mPhone.getText().toString());
//            params.addParameter("password",mPassword.getText().toString());
//            params.addParameter("verify",mVerify.getText().toString());
//            x.http().post(params, new Callback.CommonCallback<String>() {
//                @Override
//                public void onSuccess(String result) {
//                    BaseResult baseResult = Utils.parseJsonWithGson(result,BaseResult.class);
//                    if (baseResult.getCode()== Constants.HTTP_OK_200){
//                        Utils.start_Activity(ResetActivity.this,LoginActivity.class);
//                    } else {
//                        Utils.showShortToast(ResetActivity.this, baseResult.getError());
//                    }
//                }
//
//                @Override
//                public void onError(Throwable ex, boolean isOnCallback) {
//                    Utils.showShortToast(ResetActivity.this, getString(R.string.network_error));
//                }
//
//                @Override
//                public void onCancelled(CancelledException cex) {
//                    Utils.showShortToast(ResetActivity.this, getString(R.string.network_error));
//                }
//
//                @Override
//                public void onFinished() {
//
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
    public void update(){
        Utils.start_Activity(ResetActivity.this,LoginActivity.class);
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

}
