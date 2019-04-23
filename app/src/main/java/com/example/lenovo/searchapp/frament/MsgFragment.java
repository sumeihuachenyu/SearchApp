package com.example.lenovo.searchapp.frament;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.lenovo.searchapp.R;
import com.orhanobut.logger.Logger;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import me.yokeyword.fragmentation.SupportFragment;
/**
 * Created by lenovo on 2019-03-12.
 */
@ContentView(R.layout.fragment_msg)
public class MsgFragment extends SupportFragment {
    private Activity mContext;
//    @ViewInject(R.id.txt_title)
    private TextView mTitle;
//    @ViewInject(R.id.img_right)
    private ImageView mRightView;

    private String mTargetId;//对话对象id
    private ImageView conversation_back;
    private TextView conversation_name;
    private PopupWindow mPopupWindow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = x.view().inject(this,inflater,container);
        View layout = inflater.inflate(R.layout.layout_title,null);
        mTitle = (TextView)layout.findViewById(R.id.txt_title);
        mRightView = (ImageView)layout.findViewById(R.id.img_right);
        init();
        mContext = getActivity();
        indata();
        return view;
    }

    private void indata() {

    }
    private void init() {
        //进行layout_title的初始化
        Logger.d("是否为空，测试这个对象");
        mRightView.setVisibility(View.VISIBLE);//利用setVisibility(View.VISIBLE)和setVisibility(View.Gone)来显示和隐藏一个LinearLayout
        mTitle.setText(R.string.chat);
        mRightView.setImageResource(R.drawable.icon_add);
    }

//    @Event(R.id.img_right)
//    private void showSelect(View v){
//        View popupView = mContext.getLayoutInflater().inflate(R.layout.popupwindow_add, null);
//        TextView addFriend = (TextView) popupView.findViewById(R.id.tv_add_friend);
//        addFriend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mPopupWindow.dismiss();
//                Utils.start_Activity(mContext, SearchActivity.class);
//            }
//        });
//        TextView createGroup = (TextView) popupView.findViewById(R.id.tv_add_group);
//        createGroup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mPopupWindow.dismiss();
//                Utils.start_Activity(mContext, CreateGroupActivity.class);
//            }
//        });
//        mPopupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT,
//                WindowManager.LayoutParams.WRAP_CONTENT, true);
//        mPopupWindow.showAsDropDown(v,-mPopupWindow.getWidth()-v.getWidth(),9);
//        mPopupWindow.setOutsideTouchable(true);

    //}

}
