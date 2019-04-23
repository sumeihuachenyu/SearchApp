package com.example.lenovo.searchapp.person;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.searchapp.MyApplication;
import com.example.lenovo.searchapp.R;
import com.example.lenovo.searchapp.common.BaseActivity;
import com.example.lenovo.searchapp.join.MyJoinActivity;
import com.example.lenovo.searchapp.search.MySearchActivity;
import com.example.lenovo.searchapp.search.SubmitSearchActivity;
import com.example.lenovo.searchapp.utils.ActivityCollectorUtil;
import com.example.lenovo.searchapp.utils.Utils;
import com.orhanobut.logger.Logger;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by lenovo on 2019-03-15.
 */
@ContentView(R.layout.layout_person)
public class PersonActivity extends BaseActivity {
    private MyApplication myApplication;
    @ViewInject(R.id.welcom_logo)
    private TextView welcom;
    @ViewInject(R.id.head_person_img)
    private ImageView main_icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Utils.hideNavigationBar(this);
        x.view().inject(this);
        myApplication = MyApplication.getInstance();
        //需要设置头像和名称
        welcom.setText(myApplication.getUser().getUsername()+"欢迎您");
        if(myApplication.getUser().getHeadaddress() != null){
            //mImageUri.getEncodedPath()=/mnt/sdcard/bigIcon/1554644509881.jpg
            // Logger.d("mImageUri.getEncodedPath()="+mImageUri.getEncodedPath());
            //文件名称=F:\\BaiduNetdiskDownload\\androidProject\\uploadimg\\1554685537364.jpg
            ///String filename = new File(myApplication.getUser().getHeadaddress().trim()).getName();
            String fName = myApplication.getUser().getHeadaddress().trim();
            String filename = fName.substring(fName.lastIndexOf("\\")+1);
            String path = "/mnt/sdcard/bigIcon/"+filename;
            Logger.d("path="+path);
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            main_icon.setImageBitmap(bitmap);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 跳转到关于页面
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.btn_about})
    private void about(View v){
        Utils.start_Activity(this,AboutActivity.class);
    }
    /**
     * 跳转到帮助页面
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.btn_help})
    private void help(View v){
        Utils.start_Activity(this,HelpActivity.class);
    }
    /**
     * 退出登录,指的是关闭app,但是仍然处于登录状态，点击进入之后会直接进入home
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.personal_exit})
    private void exit(View v){
        myApplication.setExit(true);
        Intent intent = new Intent();// 创建Intent对象
        intent.setAction(Intent.ACTION_MAIN);// 设置Intent动作
        intent.addCategory(Intent.CATEGORY_HOME);// 设置Intent种类
        startActivity(intent);// 将Intent传递给Activity
        //Utils.start_Activity(this,LoginActivity.class);
    }
    /**
     * 切换账号，指的是到登录页面重新登录
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.change_user})
    private void changeuser(View v){
        ActivityCollectorUtil.finishAllActivity();
        myApplication.setToken("无值");
        Utils.start_Activity(this,LoginActivity.class);
    }

    /**
     * 转到消息页面
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.btn_message})
    private void getmessage(View v){
        //ActivityCollectorUtil.finishAllActivity();
        Utils.start_Activity(this,MessageActivity.class);
    }
    /**
     * 转到修改个人信息页面
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.btn_update_person})
    private void getupdate(View v){
        //ActivityCollectorUtil.finishAllActivity();
        Utils.start_Activity(this,UpdatePerson.class);
    }
    /**
     * 转到发布调查页面
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.btn_search_submit})
    private void submit(View v){
        //ActivityCollectorUtil.finishAllActivity();
        Utils.start_Activity(this,SubmitSearchActivity.class);
    }

    /**
     * 转到我的参与页面
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.btn_join})
    private void join(View v){
        //ActivityCollectorUtil.finishAllActivity();
        Utils.start_Activity(this,MyJoinActivity.class);
    }

    /**
     * 转到我的调查页面
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.btn_search})
    private void search(View v){
        //ActivityCollectorUtil.finishAllActivity();
        Utils.start_Activity(this,MySearchActivity.class);
    }
}
