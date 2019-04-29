package com.example.lenovo.searchapp.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lenovo.searchapp.MyApplication;
import com.example.lenovo.searchapp.R;
import com.example.lenovo.searchapp.common.BaseActivity;
import com.example.lenovo.searchapp.home.model.SearchAndPerson;
import com.example.lenovo.searchapp.utils.Utils;
import com.orhanobut.logger.Logger;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by lenovo on 2019-03-27.
 * 点击每一个调查item进入的页面，只使用于首页模块
 */
@ContentView(R.layout.layout_home_single_item)
public class ItemActivity extends BaseActivity {
    /**
     * 获取的该项调查的具体内容
     */
    private SearchAndPerson rvData = null;
    /**
     * 获取传递过来的searchid值
     */
    private String searchid = null;
    MyApplication myApplication = null;
    @ViewInject(R.id.home_search_item_theme)
    private TextView searchtitle;
    @ViewInject(R.id.home_search_item_person)
    private TextView searchperson;
    @ViewInject(R.id.home_search_item_time)
    private TextView searchsubmittime;
    @ViewInject(R.id.home_search_item_type)
    private TextView searchtype;
    @ViewInject(R.id.home_search_item_more)
    private TextView remarks;
    @ViewInject(R.id.key1)
    private TextView questionone;
    @ViewInject(R.id.key2)
    private TextView questtiontwo;
    @ViewInject(R.id.key3)
    private TextView questionthree;
    @ViewInject(R.id.btn_playsearch)
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        //设置参与调查按钮为View.GONE
        button.setVisibility(View.GONE);
        myApplication = MyApplication.getInstance();
        //获取从首页模块传递过来的searchid值
        Intent intent = getIntent();
        if(!intent.equals(null) || !intent.equals("")){
            this.searchid = intent.getStringExtra("searchid");//从intent对象中获得数据
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
            //根据rvData.getIsstop()的值设置参与调查按钮是否显示
            Logger.d("rvData.getIsstop()="+rvData.getIsstop());
            if("1.0".equals(rvData.getIsstop())){
                Logger.d("3333333进入");
                //进行显示
                button.setVisibility(View.VISIBLE);
            }else{
                Logger.d("4444444进入");
                //去掉
                button.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 通过searchid查询数据
     */
    private void initdata() {
        //查找数据
        for(int i = 0; i < myApplication.getSearchs().size();i++){
            if(searchid.equals(myApplication.getSearchs().get(i).getSearchid())){
                rvData = myApplication.getSearchs().get(i);
                break;
            }
        }
        //进行数据的初始化
    }

    /**
     * 跳转到上一页
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.single_item_search_home})
    private void itembackhome(View v){
        this.finish();
    }

    /**
     * 跳转到词云展示页面
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.btn_showyun})
    private void showcloud(View v){
        //需要传递searchid参数
        Utils.start_Activity(ItemActivity.this,ShowCloudActivity.class,"itemsearchid",rvData.getSearchid());
    }

    /**
     * 跳转到参与页面
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.btn_playsearch})
    private void joinsearch(View v){
        //需要传递searchid参数
        Utils.start_Activity(ItemActivity.this,JoinSearchActivity.class,"itemsearchid",rvData.getSearchid());
    }
}
