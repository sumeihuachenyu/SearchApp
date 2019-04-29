package com.example.lenovo.searchapp.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.lenovo.searchapp.R;
import com.example.lenovo.searchapp.common.API;
import com.example.lenovo.searchapp.common.BaseActivity;
import com.example.lenovo.searchapp.common.Constants;
import com.example.lenovo.searchapp.utils.ActivityCollectorUtil;
import com.example.lenovo.searchapp.utils.Utils;
import com.orhanobut.logger.Logger;

import org.xutils.common.Callback;
import org.xutils.common.task.PriorityExecutor;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2019-03-27.
 * 词云展示页面
 */
@ContentView(R.layout.layout_show_cloud)
public class ShowCloudActivity extends BaseActivity {
    /**
     * 获取传递过来的id值
     */
    private String searchid = null;
    /**
     * 可取消的任务
     */
    private Callback.Cancelable cancelable;
    /**
     * 进度条对话框
     */
    private ProgressDialog progressDialog;
    private File fileDir;
    private String saveFilePath;
    private Bitmap mBitmap;


    @ViewInject(R.id.word_cloud_img)
    private ImageView wordCloud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        //获取从首页模块传递过来的searchid值
        Intent intent = getIntent();
        if(!intent.equals(null) || !intent.equals("")){
            this.searchid = intent.getStringExtra("itemsearchid");//从intent对象中获得数据
        }
        //删除文件
        deleteFile();
        //初始化对话框
        initProgressDialog();
        //获取图片
        initData();
    }

    /**
     * 初始化对话框
     */
    private void initProgressDialog() {
        //创建进度条对话框
        progressDialog = new ProgressDialog(this);
        //设置标题
        progressDialog.setTitle("下载图片");
        //设置信息
        progressDialog.setMessage("玩命下载中...");
        //设置显示的格式
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
       progressDialog.show();
        //设置按钮
        new Thread(new Runnable() {

            @Override
            public void run() {
            // TODO Auto-generated method stub
                int i = 0;
                while (i < 100) {
                    try {
                        Thread.sleep(300);
                        // 更新进度条的进度,可以在子线程中更新进度条进度
                        progressDialog.incrementProgressBy(1);
                        i++;

                    } catch (Exception e) {
                    }
                }
                // 在进度条走完时删除Dialog
                progressDialog.dismiss();
            }
        }).start();
    }

    /**
     * 查看该文件是否存在，如果存在就删除
     */
    public void deleteFile(){
        //如果存在文件就删除该文件
        File file = new File("/mnt/sdcard/bigIcon/wordcloud.png");
        if(file.exists()){
            file.delete();
        }
    }

    /**
     * 从服务器获取图片
     */
    private void initData()  {
        //构造签名生成算法需要的数据
        Map<String, String> map = new HashMap<>();
        map.put("img", "img");
        map.put("searchid",searchid);
        //http请求
        RequestParams params = new RequestParams(API.GET_WORD_CLOUD);
        try {
            params.addParameter("sign", Utils.getSignature(map, Constants.SECRET));
            params.addBodyParameter("img","img");
            params.addBodyParameter("searchid",searchid);
            params.setAutoResume(true);//设置是否在下载是自动断点续传
            params.setSaveFilePath("/mnt/sdcard/bigIcon/wordcloud.png");
            params.setExecutor(new PriorityExecutor(2, true));//自定义线程池,有效的值范围[1, 3], 设置为3时, 可能阻塞图片加载.
            params.setCancelFast(true);//是否可以被立即停止.

            //下面的回调都是在主线程中运行的,这里设置的带进度的回调
            cancelable = x.http().get(params, new Callback.ProgressCallback<File>() {
                @Override
                public void onCancelled(CancelledException arg0) {
                    Log.i("tag", "取消"+Thread.currentThread().getName());
                }

                @Override
                public void onError(Throwable arg0, boolean arg1) {
                    Log.i("tag", "onError: 失败"+Thread.currentThread().getName());
                    progressDialog.dismiss();
                }

                @Override
                public void onFinished() {
                    Log.i("tag", "完成,每次取消下载也会执行该方法"+Thread.currentThread().getName());
                    progressDialog.dismiss();
                }

                @Override
                public void onSuccess(File arg0) {
                    Log.i("tag", "下载成功的时候执行"+Thread.currentThread().getName());
                    Logger.d("2222222="+arg0);

                    //根据图片绝对路径获取图片并显示在界面上
                    mBitmap = BitmapFactory.decodeFile(arg0.getAbsolutePath());
                    wordCloud.setImageBitmap(mBitmap);
                }

                @Override
                public void onLoading(long total, long current, boolean isDownloading) {
                    if (isDownloading) {
                        progressDialog.setProgress((int) (current*100/total));
                        Log.i("tag", "下载中,会不断的进行回调:"+Thread.currentThread().getName());
                    }
                }

                @Override
                public void onStarted() {
                    Log.i("tag", "开始下载的时候执行"+Thread.currentThread().getName());
                    progressDialog.show();
                }

                @Override
                public void onWaiting() {
                    Log.i("tag", "等待,在onStarted方法之前执行"+Thread.currentThread().getName());
                }

            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转到上一页
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.show_cloud_search_home})
    private void cloudbackhome(View v){
        ActivityCollectorUtil.removeActivity(ShowCloudActivity.this);
        this.finish();
    }
}
