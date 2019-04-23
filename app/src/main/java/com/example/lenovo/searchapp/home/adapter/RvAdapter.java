package com.example.lenovo.searchapp.home.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.lenovo.searchapp.R;
import com.example.lenovo.searchapp.home.holder.FooterHolder;
import com.example.lenovo.searchapp.home.holder.RVHolder;
import com.example.lenovo.searchapp.home.model.FooterData;
import com.example.lenovo.searchapp.home.model.SearchAndPerson;

import java.util.ArrayList;

/**
 * RecyclerView适配器
 * Created by lenovo on 2019-03-24.
 */
public class RvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        public static final int TYPE_ONE=0,TYPE_FOOT=1;
        private ArrayList<SearchAndPerson> mDatas=new ArrayList<>();
        private LayoutInflater mInflater;
        private FooterData footerData;
        private Activity context;
        /**是哪个页面点击了item*/
        private String page;

        public RvAdapter(String page,Activity context, ArrayList<SearchAndPerson> list, FooterData footerData){
                this.page = page;
                this.mDatas=list;
                this.footerData=footerData;
                this.mInflater=LayoutInflater.from(context);
                this.context =context;
        }

        // 刷新全部数据
        public void reflushData(ArrayList<SearchAndPerson> list,FooterData footerData){
                this.mDatas=list;
                this.footerData=footerData;
                this.notifyDataSetChanged();
        }

        // 刷新列表数据
        public void reflushList(ArrayList<SearchAndPerson> list){
                this.mDatas=list;
                this.notifyDataSetChanged();
        }

        // 刷新底部
        public void reflushFooterData(FooterData footerData){
                this.footerData=footerData;
                this.notifyItemChanged(getItemCount()-1);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
                RecyclerView.ViewHolder holder=null;
                switch(viewType){
                case TYPE_ONE:
                holder=new RVHolder(mInflater.inflate(R.layout.layout_home_item,parent,false));
                break;
                case TYPE_FOOT:// 底部
                holder=new FooterHolder(mInflater.inflate(R.layout.layout_footer,parent,false));
                break;
                }
                return holder;
                }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder,int position){
                int viewType=getItemViewType(position);
                switch(viewType){
                case TYPE_ONE:
                        ((RVHolder)holder).bindHolder(page,context,mDatas.get(position),position);
                        break;
                case TYPE_FOOT:// 底部
                        ((FooterHolder)holder).bindHolder(footerData);
                        break;
                }
                }

        @Override
        public int getItemCount(){
                return mDatas.size()+(footerData==null?0:1);
                }

        @Override
        public int getItemViewType(int position){
                if(position<mDatas.size())
                return mDatas.get(position).getType();
                else
                return TYPE_FOOT;
                }
}