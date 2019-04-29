package com.example.lenovo.searchapp.common;

/**
 * Created by lenovo on 2019-03-07.
 *
 *主要存放一些常量数据
 */
public class Constants {
    /** 计算请求参数签名 */
    public static final String SECRET = "ssssss";//任意字符串都可以，和服务器匹配就行
    //public static final String FEEDBACK_TYPE = "Android";

    /** 服务器返回的请求码 */
    /**表示成功*/
    public static final int STATUS_OK = 1000;
    /**表示该手机号已存在*/
    public static final int STATUS_PHONE_HAVE = 1001;
    /**表示该用户名已存在*/
    public static final int STATUS_USERNAME_HAVE = 1002;
    /**表示参数出现错误*/
    public static final int STATUS_PARAM_ERROR = 1003;
    /**表示token失效*/
    public static final int STATUS_TOKEN_ERROR = 1004;
}
