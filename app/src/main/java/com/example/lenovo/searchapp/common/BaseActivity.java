package com.example.lenovo.searchapp.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.lenovo.searchapp.R;
import com.example.lenovo.searchapp.utils.ActivityCollectorUtil;

/**
 * Created by lenovo on 2019-03-07.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollectorUtil.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorUtil.removeActivity(this);
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
    }
}
