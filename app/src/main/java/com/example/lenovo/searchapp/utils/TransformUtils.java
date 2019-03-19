package com.example.lenovo.searchapp.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lenovo on 2019-03-07.
 */
public class TransformUtils {
    /**
     * 判断是否是手机号
     *
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile) {
        // "[head]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String regex = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(mobile);
        return m.matches();
    }
    /**
     * 判断密码是否是6到20位，且包含字母和数字
     * ^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$
     * @param password
     * @return
     */
    public static boolean isPassword(String password) {
        // "[head]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String regex = "(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    /**
     * 检查输入的用户名包含字母，数字，中文以及下划线，且下划线不能出现在首位和末尾
     * @param username
     * @return
     */
    public static boolean isUsername(String username){
        String regx = "^(?!_)(?!.*?_$)[a-zA-Z0-9_\\u4e00-\\u9fa5]+$";
        Pattern p = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(username);
        return m.matches();
    }
}
