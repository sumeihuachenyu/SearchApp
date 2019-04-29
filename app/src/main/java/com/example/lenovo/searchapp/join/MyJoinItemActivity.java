package com.example.lenovo.searchapp.join;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.lenovo.searchapp.MyApplication;
import com.example.lenovo.searchapp.R;
import com.example.lenovo.searchapp.common.BaseActivity;
import com.example.lenovo.searchapp.home.ShowCloudActivity;
import com.example.lenovo.searchapp.home.model.JoinSearchPerson;
import com.example.lenovo.searchapp.utils.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by lenovo on 2019-03-28.
 * 进入参与页面中的每一项调查中
 */
@ContentView(R.layout.layout_join_single_item)
public class MyJoinItemActivity extends BaseActivity {
    /**
     * 获取的该项调查的具体内容
     */
    private JoinSearchPerson rvData = null;
    /**
     * 获取传递过来的id值
     */
    private String searchid = null;
    MyApplication myApplication = null;

    @ViewInject(R.id.Join_search_item_theme)
    private TextView searchtitle;
    @ViewInject(R.id.Join_search_item_person)
    private TextView searchperson;
    @ViewInject(R.id.Join_search_item_time)
    private TextView searchsubmittime;
    @ViewInject(R.id.Join_search_item_type)
    private TextView searchtype;
    @ViewInject(R.id.Join_search_item_more)
    private TextView remarks;
    @ViewInject(R.id.join_key1)
    private TextView questionone;
    @ViewInject(R.id.join_key2)
    private TextView questtiontwo;
    @ViewInject(R.id.join_key3)
    private TextView questionthree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        myApplication = MyApplication.getInstance();
        //获取Adapter传递过来的searchid值
        Intent intent = getIntent();
        if(!intent.equals(null) || !intent.equals("")){
            this.searchid = intent.getStringExtra("searchId");//从intent对象中获得数据
        }
        //查询数据
        initdata();
        //根据传递的值渲染页面
        initview();
    }
    /**
     * 初始化页面
     */
    private void initview() {
        if(rvData != null){
            searchtitle.setText(rvData.getSearchtitle());
            searchperson.setText(rvData.getUsername());
            searchsubmittime.setText(rvData.getSearchsubmittime());
            searchtype.setText(rvData.getSearchtype());

            remarks.setText(rvData.getRemarks());
            questionone.setText(rvData.getQuestionone());
            questtiontwo.setText(rvData.getQuestiontwo());
            questionthree.setText(rvData.getQuestionthree());
        }
    }
    /**
     * 通过searchid查询数据
     */
    private void initdata() {
        //查找数据
        for(int i = 0; i < myApplication.getJoinsearchs().size();i++){
            if(searchid.equals(myApplication.getJoinsearchs().get(i).getSearchid())){
                rvData = myApplication.getJoinsearchs().get(i);
                break;
            }
        }
    }
    /**
     * 跳转到上一页
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.single_item_my_join})
    private void itembackjoin(View v){
        this.finish();
    }

    /**
     * 跳转到词云展示页面
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.btn_show_cloud})
    private void showcloud(View v){
        //需要传递searchid参数
        Utils.start_Activity(MyJoinItemActivity.this,ShowCloudActivity.class,"itemsearchid",rvData.getSearchid());
    }

    /**
     * 跳转到参与记录页面
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.btn_join_record})
    private void showjoinrecord(View v){
        //需要传递searchid参数
        Utils.start_Activity(MyJoinItemActivity.this,MyJoinRecordActivity.class,"itemsearchid",rvData.getSearchid());
    }
}
