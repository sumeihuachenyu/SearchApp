package com.example.lenovo.searchapp.home.utils;

/**
 * 静态常量管理
 * Created by lenovo on 2019-03-24.
 */
public class ConstantUtil {
    /**
     * 底部加载更多状态
     */
    public final static int LOAD_MORE_HIDDEN = -1;// 加载隐藏
    public final static int LOAD_MORE_BEFORE = 0;// 加载前/后
    public final static int LOAD_MORE = 1;// 正在加载
    public final static int LOAD_MORE_COMPLETE = 2;// 不允许加载

    /**
     * 每页显示的最多数量
     */
    public static final int PAGE_SIZE = 10;
    public static final int PAGE_LOAD = 5;
}
