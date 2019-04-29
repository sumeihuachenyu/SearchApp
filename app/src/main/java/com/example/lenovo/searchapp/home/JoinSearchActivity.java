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
 * 参与调查页面
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
        x.view().inject(this);
        myApplication = MyApplication.getInstance();
        //获取从ItemActivity传递过来的searchid值
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
    @Event(value = {R.id.join_search_home})
    private void joinbackhome(View v){
        //this.finish();
        myApplication.setSelectPage("home");
        ActivityCollectorUtil.finishAllActivity();
        Utils.start_Activity(JoinSearchActivity.this,MainActivityTop.class);
    }

    /**
     * 点击按钮进行提交操作
     * @param v 点击视图
     */
    @Event(value = {R.id.btn_search_submit})
    private void joinsubmit(View v){
        //根据关键词1是否被选，构造数据
        if(keyOne.isChecked()){
            answerone = "1";
        }else{
            answerone = "0";
        }
        //根据关键词2是否被选，构造数据
        if(keyTwo.isChecked()){
            answertwo = "1";
        }else{
            answertwo = "0";
        }
        //根据关键词3是否被选，构造数据
        if(keyThree.isChecked()){
            answerthree = "1";
        }else{
            answerthree = "0";
        }
        //获取其他答案数据
        otheranswer = keyMore.getText().toString();
        Logger.d("answerone="+answerone+",answertwo="+answerthree+",otheranswer="+otheranswer);
        //构造签名算法需要的数据
        Map<String,String> map = new HashMap<>();
        map.put("searchid",rvData.getSearchid());
        map.put("userid",myApplication.getUser().getUserid().toString());
        map.put("answerone",answerone);
        map.put("answertwo",answertwo);
        map.put("answerthree",answerthree);
        map.put("otheranswer",otheranswer);
        //http请求
        RequestParams params = new RequestParams(API.JOIN_SEARCH);
        try {
            //传递参数
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
                        //判断是否参与成功
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
