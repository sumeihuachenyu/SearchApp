package com.example.lenovo.searchapp.SpinnerSelectAdress;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lenovo.searchapp.R;

/**
 * Created by lenovo on 2019-03-26.
 * 类型下拉框的Adapter
 */
public class ParentCategoryAdapter extends BaseAdapter {
    /**
     * 上下文对象
     */
    private Context mContext;
    /**
     * 加载的数据
     */
    private String[] str;
    /**
     * 选中的位置
     */
    private int pos;
    /**
     * 选中的是什么类型：类型|排序
     */
    private String type;

    public ParentCategoryAdapter(Context context,String[] str) {
        mContext = context;
        this.str = str;
    }

    @Override
    public int getCount() {
        return str.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            //渲染R.layout.activity_parent_category_item
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_parent_category_item, null);
            //初始化tvParentCategoryName
            holder.tvParentCategoryName = (TextView) convertView.findViewById(R.id.tv_parent_category_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //设置每一个item显示名称
        holder.tvParentCategoryName.setText(str[position]);

        //如果选中的位置等于当前位置，则设置其字体颜色为黄色，背景颜色为白色
        if(pos==position){
            holder.tvParentCategoryName.setTextColor(mContext.getResources().getColor(R.color.list_text_select_color));
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.zu_choose_right_item_bg));
        }else{
            //如果选中的位置等于当前位置，则设置其字体颜色为黑色，背景颜色为白色
            holder.tvParentCategoryName.setTextColor(mContext.getResources().getColor(android.R.color.black));
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.zu_choose_left_item_bg));
        }
        return convertView;
    }

    private class ViewHolder {
        private TextView tvParentCategoryName;
    }

    /**
     * 设置选中的位置和类型
     * @param pos
     * @param type
     */
    public void setSelectedPosition(int pos,String type) {
        this.pos = pos;
        this.type = type;
    }

    public int getPos() {
        return pos;
    }

    public String getType() {
        return type;
    }
}