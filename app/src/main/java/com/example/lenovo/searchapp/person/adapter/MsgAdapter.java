package com.example.lenovo.searchapp.person.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lenovo.searchapp.R;
import com.example.lenovo.searchapp.person.model.MsgBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2019-03-22.
 * 消息Adapter
 */
public class  MsgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /**
     * 上下文对象
     */
    private Context context;
    /**
    * 消息列表数据
    */
    private List<MsgBean> lists;
    /**
    * 标记展开的item
    */
    private int opened = -1;
    /**
     * 空数据
     */
    private final int EMPTY_VIEW = 0;
    /**
     * 不为空数据
     */
    private final int NOT_EMPTY_VIEW = 1;
    private RecyclerView.Adapter mAdapter; //需要装饰的Adapter

    public MsgAdapter(Context context) {
            this.context = context;
            lists = new ArrayList<>();
     }

    /**
     * 设置列表数据
     * @param lists
     */
    public void setLists(List<MsgBean> lists) {
            this.lists = lists;
            notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (EMPTY_VIEW == viewType) {
            //返回空布局的viewHolder
            View view = LayoutInflater.from(context).inflate(R.layout.recycleview_item_child, parent, false);
            return new EmptyViewHolder(view);
        }
        //返回存在数据的viewHolder
        View view = LayoutInflater.from(context).inflate(R.layout.recycleview_item_parent, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof EmptyViewHolder){
            return;
        }
        ((ViewHolder) viewHolder).bindView(position,lists.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        //根据传入adapter来判断是否有数据
         if(lists.size()>0){
             return NOT_EMPTY_VIEW;
         }
         return EMPTY_VIEW;
    }

    @Override
    public int getItemCount() {
       // 获取传入adapter的条目数，没有则返回 1
        if(lists.size()>0){
            return lists.size();
        }
        //位空视图保留一个条目
        return 1;
    }

    class EmptyViewHolder extends RecyclerView.ViewHolder {
        private TextView mEmptyTextView;

        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView msgTime;
        private TextView msgContent;
        private TextView msgContentMore;
        private RelativeLayout msgRl;
        private LinearLayout msgLl;

        public ViewHolder(View itemView) {
        super(itemView);
        msgTime = itemView.findViewById(R.id.msg_time);
        msgContent =  itemView.findViewById(R.id.msg_content);
        msgContentMore =  itemView.findViewById(R.id.msg_contentMore);
        msgRl = itemView.findViewById(R.id.msg_rl);
        msgLl =  itemView.findViewById(R.id.msg_ll);
        msgRl.setOnClickListener(this);
    }

    /**
     * 此方法实现列表数据的绑定和item的展开/关闭
     */
    void bindView(int pos, MsgBean bean) {
        msgTime.setText(bean.getCreatedtime());
        msgContent.setText(bean.getTitle());
        msgContentMore.setText(bean.getContent());

        if (pos == opened){
            msgLl.setVisibility(View.VISIBLE);
        } else{
            msgLl.setVisibility(View.GONE);
        }

    }
    /**
     * item的点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (opened == getAdapterPosition()) {
            //当点击的item已经被展开了, 就关闭.
            opened = -1;
            notifyItemChanged(getAdapterPosition());
        } else {
            int oldOpened = opened;
            opened = getAdapterPosition();
            notifyItemChanged(oldOpened);
            notifyItemChanged(opened);
        }
    }
  }
}