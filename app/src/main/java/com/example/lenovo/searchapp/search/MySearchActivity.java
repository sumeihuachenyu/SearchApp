package com.example.lenovo.searchapp.search;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lenovo.searchapp.MyApplication;
import com.example.lenovo.searchapp.R;
import com.example.lenovo.searchapp.SpinnerSelectAdress.SelectCategory;
import com.example.lenovo.searchapp.SpinnerSelectAdress.SelectPopupWindow;
import com.example.lenovo.searchapp.SpinnerSelectAdress.SelectPopupWindowPaixu;
import com.example.lenovo.searchapp.common.API;
import com.example.lenovo.searchapp.common.BaseActivity;
import com.example.lenovo.searchapp.common.Constants;
import com.example.lenovo.searchapp.home.SearchActivity;
import com.example.lenovo.searchapp.home.adapter.RvAdapter;
import com.example.lenovo.searchapp.home.model.FooterData;
import com.example.lenovo.searchapp.home.model.SearchAndPerson;
import com.example.lenovo.searchapp.home.utils.ConstantUtil;
import com.example.lenovo.searchapp.recyclerview.MyLinearLayoutManager;
import com.example.lenovo.searchapp.recyclerview.RecyclerViewScrollListener;
import com.example.lenovo.searchapp.utils.Utils;
import com.example.lenovo.searchapp.view.AutoSwipeRefreshLayout;
import com.example.lenovo.searchapp.view.LoadRecyclerView;
import com.google.gson.internal.LinkedTreeMap;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2019-03-28.
 * 调查模块
 */
@ContentView(R.layout.layout_my_search)
public class MySearchActivity extends BaseActivity implements RecyclerViewScrollListener.OnLoadListener, SwipeRefreshLayout.OnRefreshListener ,View.OnClickListener {
    /**自定义自动下拉刷新SwipeRefreshLayout*/
    private AutoSwipeRefreshLayout swipeLayout;
    /**下拉加载更多RecyclerView*/
    private LoadRecyclerView recyclerView;
    /**RecyclerView适配器*/
    private RvAdapter adapter;
    /**显示的数据*/
    private ArrayList<SearchAndPerson> mDatas = new ArrayList<>();
    /**底部数据*/
    private FooterData footerData;
    /**是否获取最新数据*/
    private boolean isAbleLoading = true;
    /**判断是哪个页面点击的item*/
    private String page;
    /**搜索框*/
    private EditText mSearchBox = null;
    /**类型*/
    private TextView tvZuQuyu;
    /**排序*/
    private TextView tvZuQuyupaixu;
    /**自定义下拉框*/
    private SelectPopupWindow mPopupWindow = null;
    /**自定义下拉框*/
    private SelectPopupWindowPaixu mPopupWindowpaixu = null;
    /**存储选择的排序值*/
    private int paixuid = 0;
    /**存贮选择的类型值*/
    private int typeid = 0;
    private MyApplication myApplication;
    /**
     * 存放类型的数组
     */
    private String[] types = new String[20];
    private ArrayList<LinkedTreeMap> dataBeanListTmp;//存放获取到的主页数据的临时变量
    private String[] parentStrings1 = {"校园生活","娱乐明星","生活琐事","职场生涯","教育问题","生物领域","其他"};
    private String[] parentStrings = new String[30];
    private String[] parentStringspaixu = {"降序","升序"};

    /**
     * 存放获取到的主页的数据
     */
    List<SearchAndPerson> searchs = new ArrayList<SearchAndPerson>();
    /**
     * 存放获取到的主页的数据的临时变量
     */
    List<LinkedTreeMap> searchs_temp;
    /**
     * 获取搜索框中的值
     */
    String searchValue = null;
    /**
     * 选择的类型数据
     */
    String typeStr = null;
    /**
     * 选择的排序数值
     */
    String paixuStr = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        //由于数据库中没有全部类型，则需要手动添加一个"全部类型"
        parentStrings[0] = "全部类型";
        //初始化控件
        initView();
        this.page = "search";
        Intent intent = getIntent();
        swipeLayout.autoRefresh();//进行页面的下拉刷新操作
        /**
         * 获取SearchActivity中输入的值并传入到当前的HomeActivity中进行搜索
         */
        if(!intent.equals(null) || !intent.equals("")){
            searchValue = intent.getStringExtra("searchName");//从intent对象中获得数据
            mSearchBox.setText(searchValue);
            //Utils.showShortToast(MySearchActivity.this,"得到的搜索框的值为"+searchValue);
        }
        //初始化类型下拉框数据
        initTypeData();
        //初始化首页List数据
        getHomeSearchData();
    }
    /**
     * 从服务器获取调查数据
     */
    private void getHomeSearchData() {
        //构建签名生成算法需要的数据
        Map<String,String> map = new HashMap<>();
        map.put("userid",myApplication.getUser().getUserid().toString());
        //添加搜索主题参数
        if(searchValue == null){
            map.put("searchtitle","无值");
        }else{
            map.put("searchtitle",searchValue);
        }
        //添加搜索类型参数
        if(typeStr == null){
            map.put("searchtype","无值");
        }else{
            if("全部类型".equals(typeStr)){
                map.put("searchtype","无值");
            }else{
                map.put("searchtype",typeStr);
            }
        }
        //添加搜索排序参数
        if(paixuStr == null){
            map.put("paixu","desc");
        }else{
            if("降序".equals(paixuStr)){
                map.put("paixu","desc");
            }else if("升序".equals(paixuStr)){
                map.put("paixu","asc");
            }
        }
        //http请求
       RequestParams params = new RequestParams(API.GET_PERSON_SEARCH);
        try {
            //签名参数
            params.addParameter("sign", Utils.getSignature(map, Constants.SECRET));
            params.addBodyParameter("userid",myApplication.getUser().getUserid().toString());
            if(searchValue == null){
                params.addBodyParameter("searchtitle","无值");
            }else{
                params.addBodyParameter("searchtitle",searchValue);
            }
            //搜索类型
            if(typeStr == null){
                params.addBodyParameter("searchtype","无值");
            }else{
                if("全部类型".equals(typeStr)){
                    params.addBodyParameter("searchtype","无值");
                }else{
                    params.addBodyParameter("searchtype",typeStr);
                }
            }
            //搜索排序
            if(paixuStr == null){
                params.addBodyParameter("paixu","desc");
            }else{
                if("降序".equals(paixuStr)){
                    params.addBodyParameter("paixu","desc");
                }else if("升序".equals(paixuStr)){
                    params.addBodyParameter("paixu","asc");
                }
            }
            //从服务器获取数据
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    try {
                        JSONObject baseResult = new JSONObject(result);
                        //获取数据中的状态码是否为STATUS_OK，STATUS_OK表示获取数据成功
                        if (!(baseResult.getInt("status") == Constants.STATUS_OK)){
                            Utils.showShortToast(x.app(), baseResult.getString("desc"));
                        }else if(baseResult.getInt("status") == Constants.STATUS_OK){
                            //通过Utils.parseJsonWithGson方法将json字符串转换成List<LinkedTreeMap>数据
                            searchs_temp = Utils.parseJsonWithGson(baseResult.getString("data"),ArrayList.class);

                            Logger.d("searchs_temp="+searchs_temp);
                            Logger.d("data="+baseResult.getString("data"));

                            SearchAndPerson searchAndPerson = null;
                            Logger.d("1===searchAndPerson" + searchAndPerson);
                            int num = searchs_temp.size();
                            Logger.d("num="+num);
                            if(num >0) {
                                /**
                                 * 将List<LinkedTreeMap>中每一个的数据封装到SearchAndPerson中，并将其添加到List<SearchAndPerson>数据中
                                 */
                                for (int i = 0; i < num; i++) {
                                    searchAndPerson = new SearchAndPerson();
                                    Logger.d("2==searchAndPerson" + searchAndPerson);
                                    String[] temp = new String[num];
                                    Logger.d("onFinished" + i);
                                    LinkedTreeMap map1 = searchs_temp.get(i);
                                    searchAndPerson.setPhone(map1.get("phone").toString());
                                    Logger.d("searchAndPerson.getPhone() = " + searchAndPerson.getPhone());
                                    searchAndPerson.setSearchtitle(map1.get("searchtitle").toString());
                                    Logger.d("searchAndPerson.getSearchtitle() = " + searchAndPerson.getSearchtitle());
                                    searchAndPerson.setSearchtype(map1.get("searchtype").toString());
                                    Logger.d("searchAndPerson.getSearchtype() = " + searchAndPerson.getSearchtype());
                                    searchAndPerson.setUserid(map1.get("userid").toString());
                                    Logger.d("searchAndPerson.getUserid() = " + searchAndPerson.getUserid());
                                    searchAndPerson.setRemarks(map1.get("remarks").toString());
                                    Logger.d("searchAndPerson.getRemarks() = " + searchAndPerson.getRemarks());
                                    searchAndPerson.setIsstop(map1.get("isstop").toString());
                                    Logger.d("searchAndPerson.getIsstop() = " + searchAndPerson.getIsstop());
                                    searchAndPerson.setSearchid(map1.get("searchid").toString());
                                    Logger.d("searchAndPerson.getSearchid() = " + searchAndPerson.getSearchid());
                                    searchAndPerson.setUsername(map1.get("username").toString());
                                    Logger.d("searchAndPerson.getUsername() = " + searchAndPerson.getUsername());
                                    searchAndPerson.setQuestionone(map1.get("questionone").toString());
                                    Logger.d("searchAndPerson.getQuestionone() = " + searchAndPerson.getQuestionone());
                                    searchAndPerson.setHeadaddress(map1.get("headaddress").toString());
                                    Logger.d("searchAndPerson.getHeadaddress() = " + searchAndPerson.getHeadaddress());
                                    searchAndPerson.setSearchpersonid(map1.get("searchpersonid").toString());
                                    Logger.d("searchAndPerson.getSearchpersonid() = " + searchAndPerson.getSearchpersonid());
                                    searchAndPerson.setSearchsubmittime(map1.get("searchsubmittime").toString());
                                    Logger.d("searchAndPerson.getSearchsubmittime() = " + searchAndPerson.getSearchsubmittime());
                                    searchAndPerson.setQuestiontwo(map1.get("questiontwo").toString());
                                    Logger.d("searchAndPerson.getQuestiontwo() = " + searchAndPerson.getQuestiontwo());
                                    searchAndPerson.setQuestionthree(map1.get("questionthree").toString());
                                    Logger.d("searchAndPerson.getQuestionthree() = " + searchAndPerson.getQuestionthree());
                                    Logger.d("3==searchAndPerson" + searchAndPerson);
                                    searchAndPerson.setType(RvAdapter.TYPE_ONE);
                                    searchs.add(searchAndPerson);
                                    Logger.d("）））））searchs=" + searchAndPerson);
                                }

                                Logger.d("((((searchs=" + searchs);
                                //遍历其中的数据进行显示，主要是为了查看数据是否正确
                                for (int i = 0; i < searchs.size(); i++) {
                                    Logger.d("searchs=" + searchs.get(i));
                                }

                                //将获取到的数据存放到MyApplication中方便在其他地方获取
                                myApplication.setPersonsearchs(searchs);
                                // 加载数据
                                onRefresh();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Utils.showShortToast(MySearchActivity.this, getString(R.string.network_error));
                }

                @Override
                public void onCancelled(CancelledException cex) {
                    Utils.showShortToast(MySearchActivity.this, getString(R.string.network_error));
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
     * 初始化控件
     */
    private void initView() {
        myApplication = MyApplication.getInstance();
        mSearchBox = findViewById(R.id.search_search_edit);
        mSearchBox.setOnClickListener(MySearchActivity.this);
        mSearchBox.setInputType(InputType.TYPE_NULL);
        swipeLayout = findViewById(R.id.swipe_container_my_search);
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeLayout.setOnRefreshListener(this);

        // 设置recyclerView的布局管理器
        recyclerView = findViewById(R.id.recyclerview_my_search);
        recyclerView.setLayoutManager(new MyLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setOnLoadListener(this);

        // 初始化底部数据
        footerData = new FooterData(false, false, getResources().getString(R.string.load_more_before));

        tvZuQuyu = findViewById(R.id.tvZuQuyu_search);
        tvZuQuyu.setOnClickListener(this);

        tvZuQuyupaixu = findViewById(R.id.tvZuQuyupaixu_search);
        tvZuQuyupaixu.setOnClickListener(this);
    }
    /**
     * 初始化类型数据
     */
    private void initTypeData() {
        Logger.d("myApplication.getTypes()="+myApplication.getTypes());
        //判断如果myapplication中没有types类型数据
        if(myApplication.getTypes() == null){
            //构建签名生成算法所需要的map数据
            Map<String,String> map = new HashMap<>();
            map.put("type","type");
            //http请求
            RequestParams params = new RequestParams(API.GET_TYPES);
            try {
                //签名参数
                params.addParameter("sign", Utils.getSignature(map, Constants.SECRET));
                params.addParameter("type","type");
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            JSONObject baseResult = new JSONObject(result);
                            //获取数据中的状态码是否为STATUS_OK，STATUS_OK表示获取数据成功
                            if (!(baseResult.getInt("status") == Constants.STATUS_OK)){
                                Logger.d("types="+types);
                                Utils.showShortToast(x.app(), baseResult.getString("desc"));
                            }else if(baseResult.getInt("status") == Constants.STATUS_OK){
                                //通过Utils.parseJsonWithGson方法将json字符串转换成ArrayList<LinkedTreeMap>数据
                                dataBeanListTmp = Utils.parseJsonWithGson(baseResult.getString("data"),ArrayList.class);
                                //遍历ArrayList<LinkedTreeMap>数据将类型名称存储到parentStrings数组中
                                for(int i=0,j=1; i<dataBeanListTmp.size(); i++,j++){
                                    LinkedTreeMap map = dataBeanListTmp.get(i);
                                    parentStrings[j] = map.get("typename").toString();
                                }
                                Logger.d("dataBeanListTmp="+dataBeanListTmp);
                                Logger.d("data="+baseResult.getString("data"));
                                Logger.d("parentStrings="+parentStrings);

                                //将数据存放到MyApplication中的types数组中
                                myApplication.setTypes(parentStrings);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Utils.showShortToast(MySearchActivity.this, getString(R.string.network_error));
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        Utils.showShortToast(MySearchActivity.this, getString(R.string.network_error));
                    }

                    @Override
                    public void onFinished() {

                        if(types == null){
                            parentStrings = types;
                        }else{
                            parentStrings = parentStrings1;
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            //如果parentStrings为空的化，将parentStrings1的值赋给parentStrings
            if(parentStrings == null){
                parentStrings = parentStrings1;
            }

            //进行数据的显示，方便查看数据是否成功
            Logger.d("((((types="+types+"parentStrings="+parentStrings);
        }else{
            //如果MyApplication中存在types值，那么直接取出来用即可
            this.parentStrings = myApplication.getTypes();
        }

    }
    /**
     * 自定义setAdapter
     */
    private void setAdapter() {
        if (adapter == null) {
            //如果Adapter未new对象，需要创建对象
            adapter = new RvAdapter(page,MySearchActivity.this, mDatas, footerData);
            //下拉加载更多RecyclerView，给RecyclerView添加Adapter
            recyclerView.setAdapter(adapter);
        } else {
            //刷新列表数据
            adapter.reflushList(mDatas);
        }

        // 判断是否已经加载完成
        if (mDatas == null || mDatas.size() <= 0) {
            reflashFooterView(ConstantUtil.LOAD_MORE_HIDDEN);
        } else if (mDatas.size() < ConstantUtil.PAGE_SIZE || !isAbleLoading) {
            reflashFooterView(ConstantUtil.LOAD_MORE_COMPLETE);
        } else {
            reflashFooterView(ConstantUtil.LOAD_MORE_BEFORE);
        }
    }

    // 刷新底部
    private void reflashFooterView(int index) {
        // 重构底部数据
        switch (index) {
            case ConstantUtil.LOAD_MORE_BEFORE:// 加载前/后
                recyclerView.setLoading(false);
                footerData.setShowProgressBar(false);
                footerData.setShowFooter(true);
                footerData.setTitle(getResources().getString(R.string.load_more_before));
                break;
            case ConstantUtil.LOAD_MORE:// 加载中
                recyclerView.setLoading(false);
                footerData.setShowProgressBar(true);
                footerData.setShowFooter(true);
                footerData.setTitle(getResources().getString(R.string.load_more));
                break;
            case ConstantUtil.LOAD_MORE_COMPLETE:// 不允许加载
                recyclerView.setLoading(false);
                footerData.setShowProgressBar(false);
                footerData.setShowFooter(true);
                footerData.setTitle(getResources().getString(R.string.load_more_complete));
                break;
            case ConstantUtil.LOAD_MORE_HIDDEN:// 隐藏
                recyclerView.setLoading(false);
                footerData.setShowProgressBar(false);
                footerData.setShowFooter(false);
                footerData.setTitle(getResources().getString(R.string.load_more_complete));
                break;
        }
        // 刷新底部
        if (adapter != null)
            adapter.reflushFooterData(footerData);
    }

    // 下拉刷新-一般为加载网络数据
    @Override
    public void onRefresh() {
        // 获取最新数据
        isAbleLoading = true;
        // 模拟网络加载
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mDatas == null)
                    mDatas = new ArrayList<>();
                mDatas.clear();
                if(searchs.size() > 0){
                    //如果目前从服务器获取的数据的长度大于10，则条件为j小于10
                    if(searchs.size() >= ConstantUtil.PAGE_SIZE){
                        for(int j = 0; j < ConstantUtil.PAGE_SIZE;j++){
                            mDatas.add(searchs.get(j));
                        }
                    }
                    //如果目前从服务器获取的数据的长度小于10，则条件为j小于searchs.size()
                    if(searchs.size() < ConstantUtil.PAGE_SIZE){
                        for(int j = 0; j < searchs.size();j++){
                            mDatas.add(searchs.get(j));
                        }
                    }
                    Logger.d("mDatas="+mDatas);
                }
                //将获取到的数据存放到Adapter中进行加载
                setAdapter();
                //取消加载
                swipeLayout.setRefreshing(false);
            }
        }, 3000);
        //刷新底部：加载更多之前
        reflashFooterView(ConstantUtil.LOAD_MORE_BEFORE);
    }

    // 加载更多
    @Override
    public void onLoad() {
        //如果从服务器获取到的数据长度大于目前显示的数据的长度并且需要获取最新数据
        if (myApplication.getPersonsearchs().size() > mDatas.size() && isAbleLoading) {
            // 获取更多数据
            // 模拟网络加载
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // 加载数据
                    if(searchs.size() > 0){
                        int num = mDatas.size();
                        Logger.d("num="+num);
                        //如果目前加载的数据长度+5>=从服务器获取的所有数据长度
                        if(num + ConstantUtil.PAGE_LOAD >= searchs.size()){
                            //表示可以获得最后的数据
                            for (int i = num; i < searchs.size(); i++) {
                                Logger.d("i="+i);
                                mDatas.add(searchs.get(i));
                            }
                        }
                        //如果目前加载的数据长度+5<=从服务器获取的所有数据长度
                        if(num + ConstantUtil.PAGE_LOAD <= searchs.size()){
                            //表示数据不能一次性加载完
                            for (int i = num; i < num + ConstantUtil.PAGE_LOAD; i++) {
                                Logger.d("i="+i);
                                mDatas.add(searchs.get(i));
                            }
                        }
                    }
                    //将获取到的数据存放到Adapter中进行加载
                    setAdapter();
                }
            }, 3000);
            //刷新底部：加载更多
            reflashFooterView(ConstantUtil.LOAD_MORE);
            swipeLayout.setRefreshing(false);
        } else {
            //如果从服务器获取到的数据长度等于目前显示的数据的长度并且需要获取最新数据，那么刷新底部加载完成
            reflashFooterView(ConstantUtil.LOAD_MORE_COMPLETE);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_search_edit:
                //如果点击搜索框，则进入SearchActivity之后进行搜索，需要传递的参数是page，表示这是home页面进行搜索操作，原因是SearchActivity是重用的
                Utils.start_Activity(MySearchActivity.this,SearchActivity.class,"page",page);
                break;
            case R.id.btn_submit_search_ss:
                //点击进入发布调查页面
                Utils.start_Activity(MySearchActivity.this,SubmitSearchActivity.class);
                break;
            case R.id.tvZuQuyu_search:
                //如果点击类型，则显示类型下拉框
                    if(parentStrings != null) {
                        //创建自定义的类型下拉框
                        mPopupWindow = new SelectPopupWindow(parentStrings, MySearchActivity.this, selectCategory);
                        mPopupWindow.showAsDropDown(tvZuQuyu, -5, 10);
                    }
                break;
            case R.id.tvZuQuyupaixu_search:
                //如果点击排序，则显示排序下拉框
                if(mPopupWindowpaixu == null){
                    //创建自定义的排序下拉框
                    mPopupWindowpaixu = new SelectPopupWindowPaixu(parentStringspaixu,MySearchActivity.this,selectCategory);
                    mPopupWindowpaixu.showAsDropDown(tvZuQuyupaixu, -2, 10);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 选择完成回调接口
     */
    private SelectCategory selectCategory=new SelectCategory() {

        @Override
        public void selectCategory(int parentSelectposition,boolean type) {
            if(type){
                //表示回调的是类型
                typeid = parentSelectposition;//获取选中的类型数组的索引
                typeStr=parentStrings[parentSelectposition];//获取选中的数据
                paixuStr = parentStringspaixu[paixuid];//设置之前选中的排序参数
                mDatas.clear();
                //如果选中表示需要进行数据的查询，则需要将之前的数据清空
                if(searchs.size() > 0){
                    searchs.clear();//从服务器获得的数据也需要清空
                }
                //存储在MyApplication中的从服务器获得的数据也需要清空
                if(myApplication.getPersonsearchs().size() > 0){
                    myApplication.getPersonsearchs().clear();
                }
                Logger.d("111进入类型");
                //自动刷新
                swipeLayout.autoRefresh();
                //从服务器获取数据
                getHomeSearchData();
                Logger.d("2222进入类型");
            }else{
                //表示回调的是排序
                paixuid = parentSelectposition;//获取选中的类型数组的索引
                paixuStr=parentStringspaixu[parentSelectposition];//获取选中的值
                typeStr = parentStrings[typeid];//获取之前选中的类型数据
                //如果选中表示需要进行数据的查询，则需要将之前的数据清空
                mDatas.clear();
                if(searchs.size() > 0){
                    searchs.clear();//从服务器获得的数据也需要清空
                }
                //存储在MyApplication中的从服务器获得的数据也需要清空
                if(myApplication.getPersonsearchs().size() > 0){
                    myApplication.getPersonsearchs().clear();
                }
                Logger.d("11111进入paixu");
                //自动刷新
                swipeLayout.autoRefresh();
                //从服务器获取数据
                getHomeSearchData();
                Logger.d("22222进入paixu");
            }

        }
    };

    /**
     * 跳转到发布调查
     *
     * @param v 点击视图
     */
    @Event(value = {R.id.btn_submit_search_ss})
    private void submit(View v){
        Utils.start_Activity(MySearchActivity.this,SubmitSearchActivity.class);
    }
}
