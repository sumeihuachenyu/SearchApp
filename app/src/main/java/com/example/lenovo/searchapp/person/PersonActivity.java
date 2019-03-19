package com.example.lenovo.searchapp.person;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.xutils.x;

/**
 * Created by lenovo on 2019-03-15.
 */
public class PersonActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Utils.hideNavigationBar(this);
        x.view().inject(this);
        //init();
    }
}
