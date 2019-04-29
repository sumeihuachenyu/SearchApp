package com.example.lenovo.searchapp.home.holder;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.searchapp.R;
import com.example.lenovo.searchapp.home.ItemActivity;
import com.example.lenovo.searchapp.home.SearchPersonActivity;
import com.example.lenovo.searchapp.home.model.SearchAndPerson;
import com.example.lenovo.searchapp.join.MyJoinItemActivity;
import com.example.lenovo.searchapp.search.MySearchItemActivity;
import com.example.lenovo.searchapp.utils.Utils;
import com.orhanobut.logger.Logger;

/**
 * RecyclerView的ViewHolder
 * Created by lenovo on 2019-03-24.
 */
public class RVHolder extends RecyclerView.ViewHolder {
    private Context context;
    private Activity  activity;
    private LinearLayout layout;
    private ImageView head ;
    private TextView titleTv, TimeTv;
    /**表明是哪个页面点击的item*/
    private String pagetype;

    public RVHolder(View itemView) {
        super(itemView);
        this.context = itemView.getContext();
        this.layout = itemView.findViewById(R.id.layout);
        this.titleTv = itemView.findViewById(R.id.tv_title);//标题
        this.TimeTv = itemView.findViewById(R.id.tv_desc);//发布时间
        this.head = itemView.findViewById(R.id.iv_head);//头像
    }

    public void bindHolder(String page, Activity contextHome, final SearchAndPerson rvData, final int position) {
        this.activity = contextHome;
        this.pagetype = page;
        if (rvData != null) {
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //首先判断是哪个页面点击的item
                    if(pagetype.equals("home")){
                        //将searchid传递过去，方便提供searchid查询值
                        Utils.start_Activity(activity,ItemActivity.class,"searchid",rvData.getSearchid());
                    }
                    if(pagetype.equals("join")){
                        //将searchid传递过去，方便提供searchid查询值
                        Utils.start_Activity(activity,MyJoinItemActivity.class,"searchId",rvData.getSearchid());
                    }
                    if(pagetype.equals("search")){
                        //将searchid传递过去，方便提供searchid查询值
                        Utils.start_Activity(activity,MySearchItemActivity.class,"searchId",rvData.getSearchid());
                    }
                }
            });
            //头像的点击事件处理
            head.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转到SearchPersonActivity页面，传递searchid值
                    Utils.start_Activity(activity,SearchPersonActivity.class,"headsearchid",rvData.getSearchid());
                }
            });

            //head.setImageResource(R.drawable.head);//设置头像的显示
            if(rvData.getHeadaddress() != null){
                String fName = rvData.getHeadaddress().trim();
                String filename = fName.substring(fName.lastIndexOf("\\")+1);
                String path = "/mnt/sdcard/bigIcon/"+filename;
                Logger.d("path="+path);
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                head.setImageBitmap(bitmap);
            }
            TimeTv.setText(rvData.getSearchsubmittime());//设置发布时间的值
            titleTv.setText(rvData.getSearchtitle());//设置标题的值
        }
    }
}
