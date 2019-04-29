package com.example.lenovo.searchapp.frament;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.lenovo.searchapp.MyApplication;
import com.example.lenovo.searchapp.R;
import com.example.lenovo.searchapp.common.API;
import com.example.lenovo.searchapp.common.Constants;
import com.example.lenovo.searchapp.person.LoginActivity;
import com.example.lenovo.searchapp.person.ResetActivity;
import com.example.lenovo.searchapp.utils.Utils;
import com.orhanobut.logger.Logger;

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
 */
@ContentView(R.layout.tab_inputvedify)
public class InputVertifyFragment extends Fragment {
    /**输入的验证码*/
    @ViewInject(R.id.et_reset_phone)
    private EditText editText;
    /**
     * 定义Activity变量
     */
    private Activity mContext;
    private MyApplication myApplication;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = x.view().inject(this,inflater,container);
        myApplication = MyApplication.getInstance();//获取MyApplication对象
        mContext = this.getActivity();//给参数mContext设置Activity值
        init();//初始化验证码的值
        return view;
    }

    /**
     * 初始化EditView中验证码的值
     */
    public void init(){
        Logger.d("myapplication="+myApplication.getData());
        if(myApplication.getData() != null){
            editText.setText(myApplication.getData().getCaptcha());
        }else{
            Utils.showShortToast(mContext,"请先输入手机号获取验证码");
        }
    }

    /**
     * 点击进行验证的事件触发处理方法
     * @param v
     */
    @Event(value = {R.id.tv_reset_verify})
    private void click(View v){
        //验证手机号是否为空
        if(editText.getText().toString().isEmpty()){
            Utils.showLongToast(mContext,"请重新输入手机号获取验证码");
        }

        //构建签名生成算法所需要的map数据
        Map<String,String> map = new HashMap<>();
        map.put("verify",editText.getText().toString());
        map.put("mobile",myApplication.getData().getPhone());
        //http请求
        RequestParams params = new RequestParams(API.COMPARE_VERIFY);
        try {
            //签名参数
            params.addQueryStringParameter("sign",Utils.getSignature(map, Constants.SECRET));
            //验证码参数
            params.addQueryStringParameter("verify",editText.getText().toString());
            //手机号参数
            params.addQueryStringParameter("mobile",myApplication.getData().getPhone());
            //利用get方法与服务器交互
            x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    try {
                        //将结果转换成JSONObject
                        JSONObject baseResult = new JSONObject(result);
                        //获取数据中的状态码是否为STATUS_OK，STATUS_OK表示获取数据成功
                        if (!(baseResult.getInt("status") == Constants.STATUS_OK)){
                            Utils.showShortToast(x.app(), baseResult.getString("desc"));
                        }else if(baseResult.getInt("status") == Constants.STATUS_OK){
                            //进行成功的提示
                            Utils.showShortToast(x.app(), baseResult.getString("desc"));
                            ///返回成功之后需要直接跳转到修改密码页面
                            Utils.start_Activity(mContext,ResetActivity.class);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

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
     * 退回登录（点击回到登录页面）
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.tv_reset_login2})
    private void backlogin(View v){
        Utils.start_Activity(mContext,LoginActivity.class);
    }
}
