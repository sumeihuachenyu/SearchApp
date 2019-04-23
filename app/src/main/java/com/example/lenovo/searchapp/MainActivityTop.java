package com.example.lenovo.searchapp;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.lenovo.searchapp.home.HomeActivity;
import com.example.lenovo.searchapp.join.MyJoinActivity;
import com.example.lenovo.searchapp.person.PersonActivity;
import com.example.lenovo.searchapp.search.MySearchActivity;
import com.example.lenovo.searchapp.utils.ActivityCollectorUtil;


/**
 * 进行主页面中各个模块的跳转
 */
public class MainActivityTop extends TabActivity{
//    private RadioGroup mTabButtonGroup;
//    //声明ViewPager
//    private ViewPager mViewPager;
//    //适配器
//    private FragmentPagerAdapter mAdapter;
//    //装载Fragment的集合
//    private List<Fragment> mFragments;
    /** 上次点击返回键的时间 */
    private long lastBackPressed;
    /** 两次点击的间隔时间 */
    private static final int QUIT_INTERVAL = 2000;
    public static final String TAG = MainActivityTop.class.getSimpleName();

    private RadioGroup mTabButtonGroup;
    private TabHost mTabHost;
    private MyApplication myApplication;

    public static final String TAB_MAIN = "MAIN_ACTIVITY";
    public static final String TAB_SEARCH = "SEARCH_ACTIVITY";
    public static final String TAB_CATEGORY = "CATEGORY_ACTIVITY";
    public static final String TAB_PERSONAL = "PERSONAL_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_main2);
        myApplication = MyApplication.getInstance();
        ActivityCollectorUtil.addActivity(this);
        findViewById();
        initViews();//初始化控件
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorUtil.removeActivity(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0) {
            long backPressed = System.currentTimeMillis();
            if (backPressed - lastBackPressed > QUIT_INTERVAL) {
                lastBackPressed = backPressed;
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void findViewById() {
        mTabButtonGroup = (RadioGroup) findViewById(R.id.home_radio_button_group);
    }

    //初始化控件
    private void initViews() {
        mTabHost = getTabHost();

        Intent i_main = new Intent(this, HomeActivity.class);
        Intent i_search = new Intent(this,MySearchActivity.class);
        Intent i_category = new Intent(this, MyJoinActivity.class);
        Intent i_cart = new Intent(this, PersonActivity.class);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_MAIN).setIndicator(TAB_MAIN)
                .setContent(i_main));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_SEARCH)
                .setIndicator(TAB_SEARCH).setContent(i_search));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_CATEGORY)
                .setIndicator(TAB_CATEGORY).setContent(i_category));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_PERSONAL).setIndicator(TAB_PERSONAL)
                .setContent(i_cart));

        if("home".equals(myApplication.getSelectPage())){
            mTabHost.setCurrentTabByTag(TAB_MAIN);
            RadioButton r = findViewById(R.id.home_tab_main);
            r.setChecked(true);
        }

        if("person".equals(myApplication.getSelectPage())){
            mTabHost.setCurrentTabByTag(TAB_PERSONAL);
            RadioButton r = findViewById(R.id.home_tab_personal);
            r.setChecked(true);
        }

        if("search".equals(myApplication.getSelectPage())){
            mTabHost.setCurrentTabByTag(TAB_SEARCH);
            RadioButton r = findViewById(R.id.home_tab_search);
            r.setChecked(true);
        }

        mTabButtonGroup
                .setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.home_tab_main:
                                mTabHost.setCurrentTabByTag(TAB_MAIN);
                                break;

                            case R.id.home_tab_search:
                                mTabHost.setCurrentTabByTag(TAB_SEARCH);
                                break;

                            case R.id.home_tab_category:
                                mTabHost.setCurrentTabByTag(TAB_CATEGORY);
                                break;

                            case R.id.home_tab_personal:
                                mTabHost.setCurrentTabByTag(TAB_PERSONAL);
                                break;

                            default:
                                break;
                        }
                    }
                });
    }
}
