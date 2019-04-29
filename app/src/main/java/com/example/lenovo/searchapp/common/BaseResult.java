package com.example.lenovo.searchapp.common;

/**
 * Created by lenovo on 2019-03-07.
 * 本来是为了存放获取的服务器的数据,包括服务器返回的请求码和data，但是因为利用Json可以直接获取，所以将其放弃
 */
public class BaseResult {
    private int code;

    private String error;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
