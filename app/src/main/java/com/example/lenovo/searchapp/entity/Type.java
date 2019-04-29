package com.example.lenovo.searchapp.entity;

/**
 * Created by lenovo on 2019-04-02.
 * 存储类型数据
 */
public class Type {
    private Integer typeid;

    private String typename;

    public Type(Integer typeid, String typename) {
        this.typeid = typeid;
        this.typename = typename;
    }

    public Type() {
        super();
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename == null ? null : typename.trim();
    }
}