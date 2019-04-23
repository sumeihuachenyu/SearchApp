package com.example.lenovo.searchapp.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.lenovo.searchapp.MainActivityTop;
import com.example.lenovo.searchapp.MyApplication;
import com.example.lenovo.searchapp.R;
import com.example.lenovo.searchapp.common.API;
import com.example.lenovo.searchapp.common.BaseActivity;
import com.example.lenovo.searchapp.common.Constants;
import com.example.lenovo.searchapp.home.ShowCloudActivity;
import com.example.lenovo.searchapp.home.model.SearchAndPerson;
import com.example.lenovo.searchapp.utils.ActivityCollectorUtil;
import com.example.lenovo.searchapp.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2019-03-28.
 */
@ContentView(R.layout.layout_search_single_item)
public class MySearchItemActivity extends BaseActivity {
    /**
     * 获取的该项调查的具体内容
     */
    private SearchAndPerson rvData = null;
    /**
     * 获取传递过来的id值
     */
    private String searchid = null;
    MyApplication myApplication = null;
    @ViewInject(R.id.my_search_item_theme)
    private TextView searchtitle;
    @ViewInject(R.id.my_search_item_person)
    private TextView searchperson;
    @ViewInject(R.id.my_search_item_time)
    private TextView searchsubmittime;
    @ViewInject(R.id.my_search_item_type)
    private TextView searchtype;
    @ViewInject(R.id.home_search_item_more)
    private TextView remarks;
    @ViewInject(R.id.search_key1)
    private TextView questionone;
    @ViewInject(R.id.search_key2)
    private TextView questtiontwo;
    @ViewInject(R.id.search_key3)
    private TextView questionthree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Utils.hideNavigationBar(this);
        x.view().inject(this);
        myApplication = MyApplication.getInstance();
        Intent intent = getIntent();
        if(!intent.equals(null) || !intent.equals("")){
            this.searchid = intent.getStringExtra("searchId");//从intent对象中获得数据
        }
        initdata();
        initview();
    }

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

    private void initdata() {
        //查找数据
        for(int i = 0; i < myApplication.getPersonsearchs().size();i++){
            if(searchid.equals(myApplication.getPersonsearchs().get(i).getSearchid())){
                rvData = myApplication.getPersonsearchs().get(i);
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
    @Event(value = {R.id.single_item_my_search})
    private void itembacksearch(View v){
        myApplication.setSelectPage("search");
        ActivityCollectorUtil.finishAllActivity();
        Utils.start_Activity(MySearchItemActivity.this,MainActivityTop.class);
       // this.finish();
    }

    /**
     * 跳转到词云展示页面
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.btn_show_yun})
    private void showcloud(View v){
        //Utils.start_Activity(MySearchItemActivity.this,ShowCloudActivity.class);
        Utils.start_Activity(MySearchItemActivity.this,ShowCloudActivity.class,"itemsearchid",rvData.getSearchid());
    }

    /**
     * 结束调查
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.btn_stop_search})
    private void stopsearch(View v){
        Map<String,String> map = new HashMap<>();
        map.put("searchid",rvData.getSearchid());
        RequestParams params = new RequestParams(API.STOP_SEARCH);
        try {
            params.addParameter("sign", Utils.getSignature(map, Constants.SECRET));
            params.addParameter("searchid",rvData.getSearchid());
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    try {
                        JSONObject baseResult = new JSONObject(result);
                        if (!(baseResult.getInt("status") == Constants.STATUS_OK)){
                            Utils.showShortToast(x.app(), baseResult.getString("desc"));
                        }else if(baseResult.getInt("status") == Constants.STATUS_OK){
                            Utils.showShortToast(x.app(), baseResult.getString("desc"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Utils.showShortToast(MySearchItemActivity.this, getString(R.string.network_error));
                }

                @Override
                public void onCancelled(CancelledException cex) {
                    Utils.showShortToast(MySearchItemActivity.this, getString(R.string.network_error));
                }

                @Override
                public void onFinished() {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
