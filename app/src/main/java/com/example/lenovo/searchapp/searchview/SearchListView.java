package com.example.lenovo.searchapp.searchview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by lenovo on 2019-03-25.
 * 自定义的调查搜索列表，主要存放搜索调查历时记录
 */
public class SearchListView extends ListView {
    public SearchListView(Context context) {
        super(context);
    }

    public SearchListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    //通过复写其onMeasure方法、达到对ScrollView适配的效果
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
