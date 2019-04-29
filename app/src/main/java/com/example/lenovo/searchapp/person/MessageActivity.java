package com.example.lenovo.searchapp.person;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.lenovo.searchapp.MyApplication;
import com.example.lenovo.searchapp.R;
import com.example.lenovo.searchapp.common.API;
import com.example.lenovo.searchapp.common.BaseActivity;
import com.example.lenovo.searchapp.common.Constants;
import com.example.lenovo.searchapp.person.adapter.MsgAdapter;
import com.example.lenovo.searchapp.person.model.MsgBean;
import com.example.lenovo.searchapp.utils.Utils;
import com.google.gson.internal.LinkedTreeMap;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2019-03-21.
 * 消息页面
 */
@ContentView(R.layout.layout_message)
public class MessageActivity extends BaseActivity {
    @ViewInject(R.id.recycle_view)
    private RecyclerView mRecyclerView;
    /**
     * 消息数据
     */
    private ArrayList<MsgBean> dataBeanList;
    /**
     * 处理消息数据的临时变量
     */
    private ArrayList<LinkedTreeMap> dataBeanListTmp;
    /**
     * 消息实体
     */
    private MsgBean dataBean;
    /**
     * 消息Adapter
     */
    private MsgAdapter mAdapter;
    private MyApplication myApplication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        myApplication = MyApplication.getInstance();
        //获取消息数据
        initData();
    }

    /**
     * 获取消息数据
     */
    private void initData(){
        dataBeanList = new ArrayList<>();
        Map<String,String> map = new HashMap<>();
        map.put("message","message");
        map.put("userid",myApplication.getUser().getUserid().toString());
        RequestParams params = new RequestParams(API.GET_MESSAGE);
        try {
            params.addParameter("sign", Utils.getSignature(map, Constants.SECRET));
            params.addParameter("message","message");
            params.addParameter("userid",myApplication.getUser().getUserid().toString());
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    try {
                        Logger.d("result="+result);
                        JSONObject baseResult = new JSONObject(result);
                        Logger.d("baseResult="+baseResult);
                        if (!(baseResult.getInt("status") == Constants.STATUS_OK)){
                            //需要提醒再次输入
                            Utils.showShortToast(x.app(), baseResult.getString("desc"));
                        }else if(baseResult.getInt("status") == Constants.STATUS_OK){
                            //处理数据
                            dataBeanListTmp = Utils.parseJsonWithGson(baseResult.getString("data"),ArrayList.class);
                            //遍历数据并将其封装到dataBeanListTmp中
                            for(int i=0;i<dataBeanListTmp.size();i++){
                                LinkedTreeMap map = dataBeanListTmp.get(i);
                                dataBean = new MsgBean();
                                dataBean.setMessageid(map.get("messageid").toString());
                                dataBean.setTitle(map.get("messagetitle").toString());
                                dataBean.setContent(map.get("messagecontent").toString());
                                dataBean.setUserid(map.get("userid").toString());
                                dataBean.setCreatedtime(map.get("sendtime").toString());
                                dataBeanList.add(dataBean);
                            }
                            Logger.d("dataBeanListTmp="+dataBeanListTmp);
                            Logger.d("data="+baseResult.getString("data"));
                            Logger.d("dataBeanList="+dataBeanList);
                            //设置数据
                            setData();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Utils.showShortToast(MessageActivity.this, getString(R.string.network_error));
                }

                @Override
                public void onCancelled(CancelledException cex) {
                    Utils.showShortToast(MessageActivity.this, getString(R.string.network_error));
                }

                @Override
                public void onFinished() {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将数据填充到Adapter中
     */
    private void setData(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MsgAdapter(this);
        mAdapter.setLists(dataBeanList);
        //给RecyclerList设置Adapter
        mRecyclerView.setAdapter(mAdapter);
    }
    /**
     * 跳转到上一页
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.back_message})
    private void back(View v){
        //Utils.start_Activity(MessageActivity.this,PersonActivity.class);
        this.finish();
    }

}
