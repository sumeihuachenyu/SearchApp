package com.example.lenovo.searchapp.home.model;

/**
 * 模拟RecyclerView数据，目前已经被弃用
 * Created by lenovo on 2019-03-24.
 */
public class RvData {
    private String head;
    private String searchTitle;
    private String searchTime;
    // 根据业务需求添加
    private int type;

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getSearchTitle() {
        return searchTitle;
    }

    public void setSearchTitle(String searchTitle) {
        this.searchTitle = searchTitle;
    }

    public String getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(String searchTime) {
        this.searchTime = searchTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public RvData(String head, String searchTitle, String searchTime, int type) {
        this.head = head;
        this.searchTitle = searchTitle;
        this.searchTime = searchTime;
        this.type = type;
    }

    public RvData(){

    }

    @Override
    public String toString() {
        return "RvData{" +
                "head='" + head + '\'' +
                ", searchTitle='" + searchTitle + '\'' +
                ", searchTime='" + searchTime + '\'' +
                ", type=" + type +
                '}';
    }
}
