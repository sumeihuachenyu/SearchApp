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

import android.os.Bundle;

import com.example.lenovo.searchapp.common.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;


/**
 * 监听获取短信消息
 *
 * @author 990
 *
 */
@ContentView(R.layout.layout_updateperson)
public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Utils.hideNavigationBar(this);
        x.view().inject(this);
    }

}
