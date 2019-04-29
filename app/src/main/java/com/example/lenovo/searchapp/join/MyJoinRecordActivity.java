package com.example.lenovo.searchapp.join;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.lenovo.searchapp.MyApplication;
import com.example.lenovo.searchapp.R;
import com.example.lenovo.searchapp.common.BaseActivity;
import com.example.lenovo.searchapp.home.model.JoinSearchPerson;
import com.orhanobut.logger.Logger;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by lenovo on 2019-03-28.
 * 我的参与记录页面
 */
@ContentView(R.layout.layout_join_record)
public class MyJoinRecordActivity extends BaseActivity {
    /**
     * 获取的该项调查的具体内容
     */
    private JoinSearchPerson rvData = null;
    /**
     * 获取传递过来的id值
     */
    private String searchid = null;
    MyApplication myApplication = null;
    /**关键词1*/
    @ViewInject(R.id.search_item_key1)
    private CheckBox keyOne;
    /**关键词2*/
    @ViewInject(R.id.search_item_key2)
    private CheckBox keyTwo;
    /**关键词3*/
    @ViewInject(R.id.search_item_key3)
    private CheckBox keyThree;
    /**更多*/
    @ViewInject(R.id.search_item_key_more)
    private TextView keyMore;
    @ViewInject(R.id.my_search_item_time)
    private TextView time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        myApplication = MyApplication.getInstance();
        //获取Adapter传递过来的searchid值
        Intent intent = getIntent();
        if(!intent.equals(null) || !intent.equals("")){
            this.searchid = intent.getStringExtra("itemsearchid");//从intent对象中获得数据
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
            keyOne.setText(rvData.getQuestionone());
            keyTwo.setText(rvData.getQuestiontwo());
            keyThree.setText(rvData.getQuestionthree());

            //根据获取的值来判断关键词1是否选中
            Logger.d("rvData.getAnswerone()="+rvData.getAnswerone());
            if("1".equals(rvData.getAnswerone()) || "1.0".equals(rvData.getAnswerone())){
                keyOne.setChecked(true);
            }else {
                keyOne.setChecked(false);
            }
            //根据获取的值来判断关键词2是否选中
            Logger.d("rvData.getAnswertwo()="+rvData.getAnswertwo());
            if("1".equals(rvData.getAnswertwo()) || "1.0".equals(rvData.getAnswertwo())){
                keyTwo.setChecked(true);
            }else {
                keyTwo.setChecked(false);
            }
            //根据获取的值来判断关键词3是否选中
            Logger.d("rvData.getAnswerthree()="+rvData.getAnswerthree());
            if("1".equals(rvData.getAnswerthree()) || "1.0".equals(rvData.getAnswerthree())){
                keyThree.setChecked(true);
            }else {
                keyThree.setChecked(false);
            }

            //获取其他数据
            keyMore.setText(rvData.getOtheranswer());
            //获取发布时间数据
            time.setText(rvData.getJointime());
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
    @Event(value = {R.id.join_record_search})
    private void itembackhome(View v){
        this.finish();
    }
}
