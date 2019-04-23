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
 */
public class SelectPopupWindowPaixu extends PopupWindow {
    private SelectCategory selectCategory;

    private Context context;
    private String[] parentStrings;
    private String[][] childrenStrings;

   // private ListView lvParentCategory = null;
    private ListView lvChildrenCategory= null;
    //private childrenCategoryAdapter parentCategoryAdapter = null;
    private static final String CLICK_TYPE = "type";
    private static final String CLICK_paixu = "paixu";
    private childrenCategoryAdapter childrenCategoryAdapter = null;
    private MyApplication myApplication;

    /**
     * @param parentStrings   字类别数据
     * @param activity
     * @param selectCategory  回调接口注入
     */
    //public SelectPopupWindow(String[] parentStrings, String[][] childrenStrings, Activity activity, SelectCategory selectCategory) {
    public SelectPopupWindowPaixu(String[] parentStrings, Activity activity, SelectCategory selectCategory) {
        this.selectCategory=selectCategory;
        this.parentStrings=parentStrings;
        //this.childrenStrings=childrenStrings;
        context = activity;
        myApplication = MyApplication.getInstance();
        View contentView = LayoutInflater.from(activity).inflate(R.layout.layout_quyu_choose_view_child, null);
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm); // 获取手机屏幕的大小

        this.setContentView(contentView);
        this.setWidth(dm.widthPixels);
        this.setHeight(dm.heightPixels*7/10);

        /* 设置背景显示 */
        setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.pop_bg));
        /* 设置触摸外面时消失 */
//        setOutsideTouchable(true);
//        setTouchable(true);
        setFocusable(true); /*设置点击menu以外其他地方以及返回键退出 */

        /**
         * 1.解决再次点击MENU键无反应问题
         */
        contentView.setFocusableInTouchMode(true);

        //子类别适配器
        lvChildrenCategory= (ListView) contentView.findViewById(R.id.lv_children_category);
        childrenCategoryAdapter = new childrenCategoryAdapter(activity,parentStrings);
        lvChildrenCategory.setAdapter(childrenCategoryAdapter);

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
            childrenCategoryAdapter.setSelectedPosition(position,CLICK_paixu);
            childrenCategoryAdapter.notifyDataSetChanged();
            if(selectCategory!=null){
                selectCategory.selectCategory(position,myApplication.isType());
            }
            dismiss();
        }
    };
}
