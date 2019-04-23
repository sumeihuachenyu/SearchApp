package com.example.lenovo.searchapp.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.lenovo.searchapp.MainActivityTop;
import com.example.lenovo.searchapp.MyApplication;
import com.example.lenovo.searchapp.R;
import com.example.lenovo.searchapp.common.API;
import com.example.lenovo.searchapp.common.BaseActivity;
import com.example.lenovo.searchapp.common.Constants;
import com.example.lenovo.searchapp.home.model.SearchAndPerson;
import com.example.lenovo.searchapp.utils.ActivityCollectorUtil;
import com.example.lenovo.searchapp.utils.Utils;
import com.orhanobut.logger.Logger;

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
 * Created by lenovo on 2019-03-27.
 */
@ContentView(R.layout.layout_join_search)
public class JoinSearchActivity extends BaseActivity {
    /**
     * 获取的该项调查的具体内容
     */
    private SearchAndPerson rvData = null;
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
    private EditText keyMore;
    private String answerone = null;
    private String answertwo = null;
    private String answerthree = null;
    private String otheranswer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Utils.hideNavigationBar(this);
        x.view().inject(this);
        //仅仅对页面做一个展示
        myApplication = MyApplication.getInstance();
        Intent intent = getIntent();
        if(!intent.equals(null) || !intent.equals("")){
            this.searchid = intent.getStringExtra("itemsearchid");//从intent对象中获得数据
        }
        initdata();
        initview();

        // init();
    }

    private void initview() {
        if(rvData != null){
            keyOne.setText(rvData.getQuestionone());
            keyTwo.setText(rvData.getQuestiontwo());
            keyThree.setText(rvData.getQuestionthree());
        }
    }

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
    @Event(value = {R.id.join_search_home})
    private void joinbackhome(View v){
        //this.finish();
        myApplication.setSelectPage("home");
        ActivityCollectorUtil.finishAllActivity();
        Utils.start_Activity(JoinSearchActivity.this,MainActivityTop.class);
    }

    /**
     * 提交操作
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.btn_search_submit})
    private void joinsubmit(View v){
        if(keyOne.isChecked()){
            answerone = "1";
        }else{
            answerone = "0";
        }

        if(keyTwo.isChecked()){
            answertwo = "1";
        }else{
            answertwo = "0";
        }

        if(keyThree.isChecked()){
            answerthree = "1";
        }else{
            answerthree = "0";
        }

        otheranswer = keyMore.getText().toString();

        Logger.d("answerone="+answerone+",answertwo="+answerthree+",otheranswer="+otheranswer);

        Map<String,String> map = new HashMap<>();
        map.put("searchid",rvData.getSearchid());
        map.put("userid",myApplication.getUser().getUserid().toString());
        map.put("answerone",answerone);
        map.put("answertwo",answertwo);
        map.put("answerthree",answerthree);
        map.put("otheranswer",otheranswer);
        RequestParams params = new RequestParams(API.JOIN_SEARCH);
        try {
            params.addParameter("sign", Utils.getSignature(map, Constants.SECRET));
            params.addParameter("searchid",rvData.getSearchid());
            params.addBodyParameter("userid",myApplication.getUser().getUserid().toString());
            params.addBodyParameter("answerone",answerone);
            params.addBodyParameter("answertwo",answertwo);
            params.addBodyParameter("answerthree",answerthree);
            params.addBodyParameter("otheranswer",otheranswer);
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
                    Utils.showShortToast(JoinSearchActivity.this, getString(R.string.network_error));
                }

                @Override
                public void onCancelled(CancelledException cex) {
                    Utils.showShortToast(JoinSearchActivity.this, getString(R.string.network_error));
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
