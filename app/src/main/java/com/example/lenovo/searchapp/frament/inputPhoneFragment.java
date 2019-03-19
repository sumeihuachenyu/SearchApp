package com.example.lenovo.searchapp.frament;

import android.app.Activity;
import android.os.Bundle;
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
import com.example.lenovo.searchapp.person.LoginActivity;
import com.example.lenovo.searchapp.utils.TransformUtils;
import com.example.lenovo.searchapp.utils.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * Created by lenovo on 2019-03-15.
 */
@ContentView(R.layout.tab_inputphone)
public class inputPhoneFragment extends Fragment {
    private Activity mContext;
    /**输入的手机号*/
    @ViewInject(R.id.et_input_phone)
    private EditText phone;
    /**点击获取验证码的*/
    @ViewInject(R.id.tv_inputphone_verify)
    private TextView mGetVerify;
    private SegmentedGroup mSegmentedGroup;
    private RadioButton radioButtonOne, radioButtonTwo;
    private MyApplication myApplication;

    /** 更新验证码倒计时handler */
//    private Handler mHandler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if(msg.what != 0){
//                mGetVerify.setText(msg.what+" s");
//            } else {
//                mGetVerify.setText(getString(R.string.get_verify));
//                mGetVerify.setEnabled(true);
//            }
//        }
//    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // View view = inflater.inflate(R.layout.tab_inputphone, container, false);
        View view = x.view().inject(this,inflater,container);
        mContext = this.getActivity();
        radioButtonTwo = (RadioButton)mContext.findViewById(R.id.radioButtonTwo);
        myApplication = MyApplication.getInstance();
        return view;
    }

    /**
     * 获取验证码（点击验证码文字）
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.tv_inputphone_verify})
    private void getVerify(View v){
        if(phone.getText().toString().isEmpty()){
            Utils.showShortToast(mContext,"请输入手机号");
            return;
        }else{
            if (!TransformUtils.isMobile(phone.getText().toString())){
                Utils.showShortToast(mContext,"请输入格式正确的手机号");
                return;
            }
        }
        //calcGetVerifyTime();
        String verify = "1111";
        sendVerify(phone.getText().toString());
//        Map<String,String> map = new HashMap<>();
//        map.put("mobile",phone.getText().toString());
//        RequestParams params = new RequestParams(API.GET_VERIFY_MOBILE);
//        try {
//            params.addQueryStringParameter("sign",Utils.getSignature(map, Constants.SECRET));
//            params.addQueryStringParameter("mobile",mPhone.getText().toString());
//            x.http().get(params, new Callback.CommonCallback<String>() {
//                @Override
//                public void onSuccess(String result) {
//                    BaseResult baseResult = Utils.parseJsonWithGson(result,BaseResult.class);
//                    if (!(baseResult.getCode()== Constants.HTTP_OK_200)){
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


    /**
     * 打开第二个fragment
     * @param phone
     */
    private void sendVerify(String phone){
        myApplication.setData("1111");
        radioButtonTwo.setChecked(true);
//        String verify = "1111";
//        Intent smsIntent=new Intent(Intent.ACTION_SENDTO,
//                Uri.parse("sms:"+phone));
//        smsIntent.putExtra("sms_body", "您的验证码为"+verify+",此验证码30分钟内有效");
//        try {
//            mContext.startActivity(smsIntent);
//        } catch(ActivityNotFoundException exception) {
//            Toast.makeText(mContext, "no activity", Toast.LENGTH_SHORT).show();
//        }
//        InputVertifyFragment fragment2 = new InputVertifyFragment();
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        transaction.add(R.id.foundFrameLayout, fragment2);
//        transaction.addToBackStack(null);
//        transaction.commit();

    }
    /**
     * 计算再次获取验证码的时间
     *
     */
//    private void calcGetVerifyTime() {
//        mGetVerify.setEnabled(false);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i=59;i>=0;i--) {
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
     * 退回登录（点击底部文字）
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.tv_reset_login})
    private void back(View v){
        Utils.start_Activity(mContext,LoginActivity.class);
    }
}

