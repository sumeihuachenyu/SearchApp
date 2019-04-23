package com.example.lenovo.searchapp.person.model;

/**
 * Created by lenovo on 2019-03-22.
 */
public class MsgBean {
    private String messageid;
    private String createdtime;
    private String title;
    private String content;
    private String userid;

    public String getMessageid() {
        return messageid;
    }

    public void setMessageid(String messageid) {
        this.messageid = messageid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(String createdtime) {
        this.createdtime = createdtime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MsgBean() {
    }

    public MsgBean(String messageid, String createdtime, String title, String content, String userid) {
        this.messageid = messageid;
        this.createdtime = createdtime;
        this.title = title;
        this.content = content;
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "MsgBean{" +
                "messageid='" + messageid + '\'' +
                ", createdtime='" + createdtime + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", userid='" + userid + '\'' +
                '}';
    }
}
