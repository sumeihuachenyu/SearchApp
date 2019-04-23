package com.example.lenovo.searchapp.home;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.searchapp.R;
import com.example.lenovo.searchapp.common.BaseActivity;
import com.example.lenovo.searchapp.join.MyJoinActivity;
import com.example.lenovo.searchapp.search.MySearchActivity;
import com.example.lenovo.searchapp.searchview.ICallBack;
import com.example.lenovo.searchapp.searchview.RecordSQLiteOpenHelper;
import com.example.lenovo.searchapp.searchview.SearchListView;
import com.example.lenovo.searchapp.searchview.bCallBack;
import com.example.lenovo.searchapp.utils.Utils;
import com.example.lenovo.searchapp.view.AutoClearEditText;
import com.orhanobut.logger.Logger;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * Created by lenovo on 2019-03-24.
 */
public class SearchActivity extends BaseActivity  {
    /**搜索框的输入*/
    private AutoClearEditText mEditText = null;
    /**搜索按钮*/
    private ImageView mImageButton = null;
    /**返回按钮*/
    private ImageView backSearch = null;
    /**暂无搜索历史*/
    private TextView no_records = null;
    /**初始化成员变量*/
    private Context context;
    /**清楚清除搜索历史按钮*/
    private TextView tv_clear;
    /** ListView列表 & 适配器*/
    private SearchListView listView;
    private BaseAdapter adapter;
    /***用来指示是哪个页面点击的搜索框*/
    private String page;
    /**数据库变量,用于存放历史搜索记录*/
    private RecordSQLiteOpenHelper helper = null ;
    private SQLiteDatabase db;
    // 回调接口
    private ICallBack mCallBack;// 搜索按键回调接口
    private bCallBack bCallBack; // 返回按键回调接口

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_search);
        /**实例化数据库SQLiteOpenHelper子类对象*/
        //context = MyApplication.getInstance();
        helper = new RecordSQLiteOpenHelper(SearchActivity.this);
        Logger.d("位置：create方法里");
        findViewById();
        Logger.d("位置：findViewById方法外");
        initView();
        Logger.d("位置","initView方法外");

        Intent intent = getIntent();
        if(!intent.equals(null) || !intent.equals("")){
            this.page = intent.getStringExtra("page");//从intent对象中获得数据
        }
    }

    protected void findViewById() {
        /**搜索框*/
        mEditText = (AutoClearEditText) findViewById(R.id.search_edit);
        /**搜索按钮*/
        mImageButton = (ImageView) findViewById(R.id.search_button);
        /**返回按钮*/
        backSearch = findViewById(R.id.back_search);
        /**历史搜索记录 = ListView显示*/
        listView = (SearchListView) findViewById(R.id.listView);
        /**删除历史搜索记录 按钮*/
        tv_clear = (TextView) findViewById(R.id.tv_clear);
        tv_clear.setVisibility(View.GONE);
        /**暂无搜索历史*/
        no_records = (TextView) findViewById(R.id.no_records);
        no_records.setVisibility(INVISIBLE);
    }

    protected void initView() {
        // TODO Auto-generated method stub
        mEditText.requestFocus();
        Logger.d("位置：initView");
        /**第1次进入时查询所有的历史搜索记录*/
        queryData("");

        /**点击搜索按钮*/
        mImageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Utils.showShortToast(SearchActivity.this, "亲，该功能暂未开放");
                // 1. 点击搜索按键后，根据输入的搜索字段进行查询
                // 注：由于此处需求会根据自身情况不同而不同，所以具体逻辑由开发者自己实现，此处仅留出接口
                if (!(mCallBack == null)){
                    mCallBack.SearchAciton(mEditText.getText().toString());
                }
                //Toast.makeText(SearchActivity.this, "需要搜索的是" + mEditText.getText(), Toast.LENGTH_SHORT).show();

                // 2. 点击搜索键后，对该搜索字段在数据库是否存在进行检查（查询）->> 关注1
                boolean hasData = hasData(mEditText.getText().toString().trim());
                // 3. 若存在，则不保存；若不存在，则将该搜索字段保存（插入）到数据库，并作为历史搜索记录
                if (!hasData) {
                    insertData(mEditText.getText().toString().trim());
                    queryData("");
                }

                if(page.equals("home")){
                    Utils.start_Activity(SearchActivity.this,HomeActivity.class,"searchName",mEditText.getText().toString().trim());
                    finish();
                }

                if(page.equals("join")){
                    Utils.start_Activity(SearchActivity.this,MyJoinActivity.class,"searchName",mEditText.getText().toString().trim());
                    finish();
                }

                if(page.equals("search")){
                    Utils.start_Activity(SearchActivity.this,MySearchActivity.class,"searchName",mEditText.getText().toString().trim());
                    finish();
                }

            }
        });
        /**点击返回按钮*/
        backSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SearchActivity.this.finish();
            }
        });
        /**
         * "清空搜索历史"按钮
         */
        tv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 清空数据库
                deleteData();
                // 模糊搜索空字符 = 显示所有的搜索历史（此时是没有搜索记录的）
                queryData("");
            }
        });

        /**
         * 监听输入键盘更换后的搜索按键
         * 调用时刻：点击键盘上的搜索键时
         */
        mEditText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    // 1. 点击搜索按键后，根据输入的搜索字段进行查询
                    // 注：由于此处需求会根据自身情况不同而不同，所以具体逻辑由开发者自己实现，此处仅留出接口
                    if (!(mCallBack == null)){
                        mCallBack.SearchAciton(mEditText.getText().toString());
                    }
                    //Toast.makeText(SearchActivity.this, "需要搜索的是" + mEditText.getText(), Toast.LENGTH_SHORT).show();

                    // 2. 点击搜索键后，对该搜索字段在数据库是否存在进行检查（查询）->> 关注1
                    boolean hasData = hasData(mEditText.getText().toString().trim());
                    // 3. 若存在，则不保存；若不存在，则将该搜索字段保存（插入）到数据库，并作为历史搜索记录
                    if (!hasData) {
                        insertData(mEditText.getText().toString().trim());
                        queryData("");
                    }

                    if(page.equals("home")){
                        Utils.start_Activity(SearchActivity.this,HomeActivity.class,"searchName",mEditText.getText().toString().trim());
                        finish();
                    }

                    if(page.equals("join")){
                        Utils.start_Activity(SearchActivity.this,MyJoinActivity.class,"searchName",mEditText.getText().toString().trim());
                        finish();
                    }

                    if(page.equals("search")){
                        Utils.start_Activity(SearchActivity.this,MySearchActivity.class,"searchName",mEditText.getText().toString().trim());
                        finish();
                    }
                }
                return false;
            }
        });

        /**
         * 搜索框的文本变化实时监听
         */
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            // 输入文本后调用该方法
            @Override
            public void afterTextChanged(Editable s) {
                // 每次输入后，模糊查询数据库 & 显示
                // 注：若搜索框为空,则模糊搜索空字符 = 显示所有的搜索历史
                String tempName = mEditText.getText().toString();
                queryData(tempName); // ->>关注1

            }
        });

        /**
         * 搜索记录列表（ListView）监听
         * 即当用户点击搜索历史里的字段后,会直接将结果当作搜索字段进行搜索
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // 获取用户点击列表里的文字,并自动填充到搜索框内
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                String name = textView.getText().toString();
                mEditText.setText(name);
                Toast.makeText(SearchActivity.this, name, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 模糊查询数据 & 显示到ListView列表上
     */
    private void queryData(String tempName) {
        // 1. 模糊搜索
        Logger.d("判断是否空="+ helper.getReadableDatabase());
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name like '%" + tempName + "%' order by id desc ", null);
        // 2. 创建adapter适配器对象 & 装入模糊搜索的结果
        adapter = new SimpleCursorAdapter(SearchActivity.this, android.R.layout.simple_list_item_1, cursor, new String[] { "name" },
                new int[] { android.R.id.text1 }, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        // 3. 设置适配器
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        System.out.println(cursor.getCount());
        // 当输入框为空 & 数据库中有搜索记录时，显示 "删除搜索记录"按钮
        if (tempName.equals("") && cursor.getCount() != 0){
            no_records.setVisibility(View.GONE);
            tv_clear.setVisibility(VISIBLE);
        }else if(!tempName.equals("")&& cursor.getCount() != 0){
            //输入框有值且模糊查询有值
            no_records.setVisibility(View.GONE);
            tv_clear.setVisibility(VISIBLE);
        }else if(!tempName.equals("")&& cursor.getCount() == 0){
            //输入框有值且模糊查询无值
            tv_clear.setVisibility(INVISIBLE);
            no_records.setVisibility(VISIBLE);
        }
        else {
            tv_clear.setVisibility(INVISIBLE);
            no_records.setVisibility(VISIBLE);
        }
    }
    /**
     * 关注2：清空数据库
     */
    private void deleteData() {

        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
        tv_clear.setVisibility(INVISIBLE);
    }
    /**
     * 关注3
     * 检查数据库中是否已经有该搜索记录
     */
    private boolean hasData(String tempName) {
        // 从数据库中Record表里找到name=tempName的id
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        //  判断是否有下一个
        return cursor.moveToNext();
    }
    /**
     * 关注4
     * 插入数据到数据库，即写入搜索字段到历史搜索记录
     */
    private void insertData(String tempName) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name) values('" + tempName + "')");
        db.close();
    }
    /**
     * 点击键盘中搜索键后的操作，用于接口回调
     */
    public void setOnClickSearch(ICallBack mCallBack){
        this.mCallBack = mCallBack;

    }

    /**
     * 点击返回后的操作，用于接口回调
     */
    public void setOnClickBack(bCallBack bCallBack){
        this.bCallBack = bCallBack;

    }
}
