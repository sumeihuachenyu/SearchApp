package com.example.lenovo.searchapp.frament;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.lenovo.searchapp.MyApplication;
import com.example.lenovo.searchapp.R;
import com.example.lenovo.searchapp.common.API;
import com.example.lenovo.searchapp.common.Constants;
import com.example.lenovo.searchapp.entity.Captcha;
import com.example.lenovo.searchapp.person.LoginActivity;
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
 * Created by lenovo on 2019-03-15.
 * 输入手机号，获取验证码的操作
 */
@ContentView(R.layout.tab_inputphone)
public class inputPhoneFragment extends Fragment {
    /**
     * 定义Activity变量
     */
    private Activity mContext;
    /**输入的手机号*/
    @ViewInject(R.id.et_input_phone)
    private EditText phone;
    /**点击获取验证码的*/
    @ViewInject(R.id.tv_inputphone_verify)
    private TextView mGetVerify;
    private RadioButton  radioButtonTwo;
    private MyApplication myApplication;

    /** 更新验证码倒计时handler */
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what != 0){
                mGetVerify.setText(msg.what+" s");
            } else if(msg.what == 0){
                mGetVerify.setEnabled(true);
                mGetVerify.setText("获取验证码");
                //mGetVerify.setEnabled(true);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = x.view().inject(this,inflater,container);
        mContext = this.getActivity();//给参数mContext设置Activity值
        radioButtonTwo = mContext.findViewById(R.id.radioButtonTwo);//查找R.layout下的layout_top布局文件中的"输入验证码"按钮
        myApplication = MyApplication.getInstance();//获取MyApplication对象
        return view;
    }

    /**
     * 获取验证码（点击验证码文字）
     * 获取验证码按钮的触发事件
     * @param v 点击视图
     */
    @Event(value = {R.id.tv_inputphone_verify})
    private void getVerify(View v){
        //判断是否为空
        if(phone.getText().toString().isEmpty()){
            Utils.showShortToast(mContext,"请输入手机号");
            return;
        }else{
            //判断手机格式是否正确
            if (!TransformUtils.isMobile(phone.getText().toString())){
                Utils.showShortToast(mContext,"请输入格式正确的手机号");
                return;
            }
        }
        calcGetVerifyTime();//调用再次计算再次获取验证码时间的方法

        //构建签名生成算法所需要的map数据
        Map<String,String> map = new HashMap<>();
        map.put("mobile",phone.getText().toString());
        //http请求
        RequestParams params = new RequestParams(API.GET_VERIFY_MOBILE);
        try {
            //签名参数
            params.addQueryStringParameter("sign",Utils.getSignature(map, Constants.SECRET));
            //手机参数
            params.addQueryStringParameter("mobile",phone.getText().toString());
            //利用get方法与服务器交互
            x.http().get(params, new Callback.CommonCallback<String>() {
                /**
                 * 返回数据成功
                 * @param result
                 */
                @Override
                public void onSuccess(String result) {
                    //BaseResult baseResult = Utils.parseJsonWithGson(result,BaseResult.class);
                    try {
                        //将结果转换成JSONObject
                        JSONObject baseResult = new JSONObject(result);
                        //获取数据中的状态码是否为STATUS_OK，STATUS_OK表示获取数据成功
                        if (!(baseResult.getInt("status") == Constants.STATUS_OK)){
                            Utils.showShortToast(x.app(), baseResult.getString("desc"));
                        }else if(baseResult.getInt("status") == Constants.STATUS_OK){
                            //通过Utils.parseJsonWithGson方法将json字符串转换成实体
                            Captcha captcha = Utils.parseJsonWithGson(baseResult.getString("data"),Captcha.class);
                            //设置"获取验证码"的TextView是不可修改的
                            mGetVerify.setEnabled(false);
                            //向Myapplication中存储captcha值，方便将数据传递到另一个Fragment中显示值
                            myApplication.setData(captcha);
                            //设置R.layout下的layout_top布局文件中的第二个单选按钮被选中，即可进入输入验证码页面
                            radioButtonTwo.setChecked(true);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                /**
                 * 返回出错
                 * @param ex
                 * @param isOnCallback
                 */
                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Utils.showShortToast(mContext, getString(R.string.network_error));
                }

                @Override
                public void onCancelled(CancelledException cex) {
                    Utils.showShortToast(mContext, getString(R.string.network_error));
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
     * 计算再次获取验证码的时间
     *
     */
    private void calcGetVerifyTime() {
        mGetVerify.setEnabled(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=59;i>=0;i--) {
                    try {
                        Thread.sleep(1000);
                        mHandler.sendEmptyMessage(i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    /**
     * 退回登录（点击退回到登录页面）
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.tv_reset_login})
    private void back(View v){
        Utils.start_Activity(mContext,LoginActivity.class);
    }
}

