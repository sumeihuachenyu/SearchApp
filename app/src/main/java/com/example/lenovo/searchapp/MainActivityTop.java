package com.example.lenovo.searchapp;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.TabHost;

import com.example.lenovo.searchapp.person.ResetActivity;


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

    public static final String TAG = MainActivityTop.class.getSimpleName();

    private RadioGroup mTabButtonGroup;
    private TabHost mTabHost;

    public static final String TAB_MAIN = "MAIN_ACTIVITY";
    public static final String TAB_SEARCH = "SEARCH_ACTIVITY";
    public static final String TAB_CATEGORY = "CATEGORY_ACTIVITY";
    public static final String TAB_PERSONAL = "PERSONAL_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_main2);
        findViewById();
        initViews();//初始化控件
    }

    private void findViewById() {
        mTabButtonGroup = (RadioGroup) findViewById(R.id.home_radio_button_group);
    }

    //初始化控件
    private void initViews() {
        mTabHost = getTabHost();

        Intent i_main = new Intent(this, ResetActivity.class);
        Intent i_search = new Intent(this,ResetActivity.class);
        Intent i_category = new Intent(this, ResetActivity.class);
        Intent i_cart = new Intent(this, ResetActivity.class);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_MAIN).setIndicator(TAB_MAIN)
                .setContent(i_main));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_SEARCH)
                .setIndicator(TAB_SEARCH).setContent(i_search));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_CATEGORY)
                .setIndicator(TAB_CATEGORY).setContent(i_category));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_PERSONAL).setIndicator(TAB_PERSONAL)
                .setContent(i_cart));
        mTabHost.setCurrentTabByTag(TAB_MAIN);

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
