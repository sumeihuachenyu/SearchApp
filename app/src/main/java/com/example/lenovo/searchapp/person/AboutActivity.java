package com.example.lenovo.searchapp.person;

import android.os.Bundle;
import android.view.View;

import com.example.lenovo.searchapp.R;
import com.example.lenovo.searchapp.common.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

/**
 * Created by lenovo on 2019-03-20.
 * 关于页面
 */
@ContentView(R.layout.layout_about)
public class AboutActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 跳转到上一页
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.back_about})
    private void back(View v){
        this.finish();
    }

}
