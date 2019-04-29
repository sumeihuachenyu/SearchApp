package com.example.lenovo.searchapp.common;

/**
 * Created by lenovo on 2019-03-07.
 *
 * 主要存储http请求地址
 */
public class API {
    //http地址的公共部分
    public static final String BASE_URL = "http://10.0.2.2:8080";

    //通过手机去获取验证码
    public static final String GET_VERIFY_MOBILE = BASE_URL+"/getVerifyByMobiles";
    //进行验证码的验证
    public static final String COMPARE_VERIFY = BASE_URL+"/VerifyCompare";
    //登录注册
    public static final String REGISTER_WITH_MOBILE = BASE_URL + "/user/register";
    //进行登录
    public static final String LOGIN_WITH_MOBILE = BASE_URL + "/user/login";
    //进行密码的验证
    public static final String RESET_PASSWORD = BASE_URL+"/user/resetPassword";
    //进行个人信息的修改
    public static final String UPDATE_PERSON = BASE_URL+"/user/updatePerson";
    //进行个人信息的修改
    public static final String UPDATE_PERSON_HEADIMG = BASE_URL+"/user/updatePersonHeadImg";
    //获取系统消息
    public static final String GET_MESSAGE = BASE_URL+"/message/getmessages";
    //获取类型信息
    public static final String GET_TYPES = BASE_URL+"/type/getTypes";
    //发布调查
    public static final String INSERT_SEARCH = BASE_URL+"/search/insert";
    //获取主页数据
    public static final String GET_HOME_SEARCH = BASE_URL+"/search/getHomes";
    //发布调查
    public static final String GET_PERSON_SEARCH = BASE_URL+"/search/getPersonSearches";
    //参与调查
    public static final String JOIN_SEARCH = BASE_URL+"/join/insert";
    //结束调查
    public static final String STOP_SEARCH = BASE_URL+"/search/updateisStop";
    //获取参与调查
    public static final String GET_JOIN_SEARCH = BASE_URL+"/search/getJoinSearches";
    //获取词云图片
    public static final String GET_WORD_CLOUD = BASE_URL+"/cloud/show";


}
