package com.example.lenovo.searchapp.SpinnerSelectAdress;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.lenovo.searchapp.MyApplication;
import com.example.lenovo.searchapp.R;
import com.example.lenovo.searchapp.utils.Utils;
import com.orhanobut.logger.Logger;

/**
 * Created by lenovo on 2019-03-26.
 * 自定义的类型下拉框
 */
public class SelectPopupWindow extends PopupWindow {
    /**
     * 选择完成回调接口
     */
    private SelectCategory selectCategory;
    private MyApplication myApplication;
    /**
     * 上下文对象
     */
    private Context context;
    /**
     * 填充的类型数据
     */
    private String[] parentStrings;
    private ListView lvParentCategory = null;
    private ParentCategoryAdapter parentCategoryAdapter = null;
    private static final String CLICK_TYPE = "type";
    /**
     * @param parentStrings   字类别数据
     * @param activity
     * @param selectCategory  回调接口注入
     */
    public SelectPopupWindow(String[] parentStrings, Activity activity, SelectCategory selectCategory) {
        this.selectCategory=selectCategory;
        this.parentStrings=parentStrings;
        context = activity;
        View contentView = LayoutInflater.from(activity).inflate(R.layout.layout_quyu_choose_view, null);
        //获取屏幕信息DisplayMetrics
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm); // 获取手机屏幕的大小
        //设置下拉框的显示界面
        this.setContentView(contentView);
        this.setWidth(dm.widthPixels);//设置大小
        this.setHeight(dm.heightPixels*7/10);//设置高度

        /* 设置背景显示 */
        setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.pop_bg));
        setFocusable(true); /*设置点击menu以外其他地方以及返回键退出 */

        /**
         * 1.解决再次点击MENU键无反应问题
         */
        contentView.setFocusableInTouchMode(true);
        //获取的layout_quyu_choose_view的ListView
        lvParentCategory= contentView.findViewById(R.id.lv_parent_category);
        //创建类别适配器
        parentCategoryAdapter = new ParentCategoryAdapter(activity,parentStrings);
        //给ListView绑定Adapter
        lvParentCategory.setAdapter(parentCategoryAdapter);
        //给ListView中的设置ItemClick监听
        lvParentCategory.setOnItemClickListener(parentItemClickListener);
        //获取Myapplication对象
        myApplication = MyApplication.getInstance();
    }

    /**
     * 类别点击事件
     */
    private AdapterView.OnItemClickListener parentItemClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
            myApplication.setType(true);
            String parentStr=parentStrings[position];
            Logger.d("点击的类型="+parentStr+"位置="+position);
            Utils.showShortToast(context,"点击了"+parentStr);
            //给Adapter设置选中的位置和选中的类型，已被弃用，因为无法在HomeActivity中获取当前设置的值
            parentCategoryAdapter.setSelectedPosition(position,CLICK_TYPE);
            parentCategoryAdapter.notifyDataSetChanged();
            if(selectCategory!=null){
                //给选择完成回调接口设置选中的位置和选中的类型，方便在HomeActivity中进行获取
                selectCategory.selectCategory(position,myApplication.isType());
            }
            dismiss();
        }
    };
}
