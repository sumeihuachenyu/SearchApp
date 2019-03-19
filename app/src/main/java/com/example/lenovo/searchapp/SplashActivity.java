package com.example.lenovo.searchapp;
//
//import android.support.v4.app.FragmentActivity;
//
//import org.xutils.view.annotation.ContentView;
//
///**
// * Created by lenovo on 2019-03-12.
// */
//@ContentView(R.layout.layout_splash)
//public class SplashActivity extends FragmentActivity {
////    @ViewInject(R.id.vp_splash_view)
////    private ViewPager viewPager;
////    private ArrayList<Fragment> fragments;
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        x.view().inject(this);
////        init();
////    }
////
////    private void init() {
////        fragments = new ArrayList<>();
////        fragments.add(SplashItemFragment.newInstance(R.layout.splash_item,R.drawable.boot_imageview_1));
////        fragments.add(SplashItemFragment.newInstance(R.layout.splash_item,R.drawable.boot_imageview_2));
////        fragments.add(SplashItemFragment.newInstance(R.layout.splash_item_in,R.drawable.boot_imageview_3));
////        viewPager.setAdapter(new SplashPagerAdapter(getSupportFragmentManager()));
////    }
////
////    @Event(R.id.btn_splash_start)
////    public void startMain(View view) {
////        finish();
////        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
////    }
////
////    class SplashPagerAdapter extends FragmentPagerAdapter {
////
////        public SplashPagerAdapter(FragmentManager fm) {
////            super(fm);
////        }
////
////        @Override
////        public Fragment getItem(int position) {
////            return fragments.get(position);
////        }
////
////        @Override
////        public int getCount() {
////            return fragments == null ? 0 : fragments.size();
////        }
////    }
//}

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 监听获取短信消息
 *
 * @author 990
 *
 */
public class SplashActivity extends Activity {

    private TextView messageTxtv, messageSender, messageTime;
    private EditText pwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
    }


    /**
     * 实例化控件，及把监听到的信息展示再TextView上
     */
    private void initView() {
        messageTxtv = (TextView) findViewById(R.id.message_txtv);
        messageSender = (TextView) findViewById(R.id.message_sender);
        messageTime = (TextView) findViewById(R.id.message_time);
        pwd = (EditText) findViewById(R.id.pwd_edit);
        MessageReceiver mReceiver = new MessageReceiver();
        mReceiver.setOnMessageClickListener(new OnMessageClickListener() {


            @Override
            public void OnMessageClick(String message, String sender,
                                       String time) {


                messageSender.setText(sender);
                messageTxtv.setText(message);
                messageTime.setText(time);


// 获取验证码
                pwd.setText("您的验证码是：" + getDynamicPwd(message));
            }
        });
    }


    /**
     * 截取六位动态码 从字符串中截取连续6位数字组合 ([0-9]{" + 6 + "})截取六位数字 进行前后断言不能出现数字
     * 用于从短信中获取动态密码
     *
     * @param pwd
     * @return
     */
    private String getDynamicPwd(String pwd) {


        Pattern pattern = Pattern.compile("(?<![0-9])([0-9]{" + 6
                + "})(?![0-9])");
        Matcher matcher = pattern.matcher(pwd);
        String dPwd = null;
        while (matcher.find()) {
            Log.i("tag", matcher.group());
            dPwd = matcher.group();
        }
        return dPwd;
    }
}
