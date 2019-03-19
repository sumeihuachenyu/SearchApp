package com.example.lenovo.searchapp.common;

/**
 * Created by lenovo on 2019-03-07.
 *
 * 主要存储http请求地址
 */
public class API {
    public static final String BASE_URL = "http://192.168.137.head:8080";

    //通过手机去获取验证码
    public static final String GET_VERIFY_MOBILE = BASE_URL+"/getVerifyByMobile";

    //登录注册
    public static final String REGISTER_WITH_MOBILE = BASE_URL + "/Auth/registerWithMobile";
    public static final String LOGIN_WITH_MOBILE = BASE_URL + "/Auth/loginWithMobile";
    public static final String RESET_PASSWORD = BASE_URL+"/Auth/resetPassword";

    //
    public static final String GET_USERINFO = BASE_URL + "/IM/getUserInfo";
}
