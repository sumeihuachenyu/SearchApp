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
 * 自定义的排序下拉框
 */
public class SelectPopupWindowPaixu extends PopupWindow {
    /**
     * 选择完成回调接口
     */
    private SelectCategory selectCategory;
    /**
     * 上下文对象
     */
    private Context context;
    /**
     * 填充的排序数据
     */
    private String[] parentStrings;
    private ListView lvChildrenCategory= null;
    private static final String CLICK_paixu = "paixu";
    private childrenCategoryAdapter childrenCategoryAdapter = null;
    private MyApplication myApplication;

    /**
     * @param parentStrings   字类别数据
     * @param activity
     * @param selectCategory  回调接口注入
     */
    public SelectPopupWindowPaixu(String[] parentStrings, Activity activity, SelectCategory selectCategory) {
        this.selectCategory=selectCategory;
        this.parentStrings=parentStrings;
        context = activity;
        myApplication = MyApplication.getInstance();
        View contentView = LayoutInflater.from(activity).inflate(R.layout.layout_quyu_choose_view_child, null);
        //获取屏幕信息DisplayMetrics
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm); // 获取手机屏幕的大小
        //设置下拉框的显示界面
        this.setContentView(contentView);
        this.setWidth(dm.widthPixels);
        this.setHeight(dm.heightPixels*7/10);

        /* 设置背景显示 */
        setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.pop_bg));
        setFocusable(true); /*设置点击menu以外其他地方以及返回键退出 */

        /**
         * 1.解决再次点击MENU键无反应问题
         */
        contentView.setFocusableInTouchMode(true);

        //获取的layout_quyu_choose_view_child的ListView
        lvChildrenCategory= (ListView) contentView.findViewById(R.id.lv_children_category);
        //创建排序适配器
        childrenCategoryAdapter = new childrenCategoryAdapter(activity,parentStrings);
        //给ListView绑定Adapter
        lvChildrenCategory.setAdapter(childrenCategoryAdapter);
        //给ListView中的设置ItemClick监听
        lvChildrenCategory.setOnItemClickListener(childrenItemClickListener);
    }

    /**
     * 子类别点击事件
     */
    private AdapterView.OnItemClickListener childrenItemClickListener=new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            myApplication.setType(false);
            String parentStr=parentStrings[position];
            Logger.d("点击的排序="+parentStr+"位置="+position);
            Utils.showShortToast(context,"点击了"+parentStr);
            //给Adapter设置选中的位置和选中的类型，已被弃用，因为无法在HomeActivity中获取当前设置的值
            childrenCategoryAdapter.setSelectedPosition(position,CLICK_paixu);
            childrenCategoryAdapter.notifyDataSetChanged();
            if(selectCategory!=null){
                //给选择完成回调接口设置选中的位置和选中的类型，方便在HomeActivity中进行获取
                selectCategory.selectCategory(position,myApplication.isType());
            }
            dismiss();
        }
    };
}
