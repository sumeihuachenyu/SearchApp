package com.example.lenovo.searchapp.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.searchapp.MyApplication;
import com.example.lenovo.searchapp.R;
import com.example.lenovo.searchapp.common.BaseActivity;
import com.example.lenovo.searchapp.home.model.SearchAndPerson;
import com.orhanobut.logger.Logger;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by lenovo on 2019-03-27.
 * 点击头像会进入到用户页面
 */
@ContentView(R.layout.layout_home_person)
public class SearchPersonActivity extends BaseActivity {
    private MyApplication myApplication = null;
    /**
     * 获取的该项调查的具体内容
     */
    private SearchAndPerson rvData = null;
    /**
     * 获取传递过来的id值
     */
    private String searchid = null;
    @ViewInject(R.id.home_person_head)
    private ImageView imageView;
    @ViewInject(R.id.home_person_phone)
    private TextView phone;
    @ViewInject(R.id.home_person_username)
    private TextView username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        myApplication = MyApplication.getInstance();
        //获取从首页模块传递过来的searchid值
        Intent intent = getIntent();
        if(!intent.equals(null) || !intent.equals("")){
            this.searchid = intent.getStringExtra("headsearchid");//从intent对象中获得数据
        }
        Logger.d("searchid="+searchid);
        //查询数据
        initdata();
        Logger.d("searchPerson="+rvData);
        //根据传递的值渲染页面
        initview();
    }
    /**
     * 初始化页面
     */
    private void initview() {
        if(rvData != null){
            if(rvData.getHeadaddress() != null){
                String fName = rvData.getHeadaddress().trim();
                String filename = fName.substring(fName.lastIndexOf("\\")+1);
                String path = "/mnt/sdcard/bigIcon/"+filename;
                Logger.d("path="+path);
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                imageView.setImageBitmap(bitmap);
            }
            phone.setText(rvData.getPhone());
            username.setText(rvData.getUsername());
        }
    }
    /**
     * 通过searchid查询数据
     */
    private void initdata() {
        //查找数据
        for(int i = 0; i < myApplication.getSearchs().size();i++){
            if(searchid.equals(myApplication.getSearchs().get(i).getSearchid())){
                Logger.d("myApplication.getSearchs()="+myApplication.getSearchs());
                rvData = myApplication.getSearchs().get(i);
                break;
            }
        }
    }

    /**
     * 跳转到上一页
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.person_search_home})
    private void backhome(View v){
        this.finish();
    }
}
