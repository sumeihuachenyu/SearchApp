package com.example.lenovo.searchapp.home.model;

/**
 * Created by lenovo on 2019-04-11.
 * 只包含search和Person数据，主要在首页和调查页面使用
 */
public class SearchAndPerson {
    private String searchid;
    // 根据业务需求添加
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private String searchtitle;

    private String searchsubmittime;

    private String searchtype;

    private String searchpersonid;

    private String questionone;

    private String questiontwo;

    private String questionthree;

    private String remarks;

    private String isstop;

    private String userid;

    private String phone;

    private String username;


    private String headaddress;

    public SearchAndPerson(){}
    public SearchAndPerson(String searchid, String searchtitle, String searchsubmittime, String searchtype, String searchpersonid, String questionone, String questiontwo, String questionthree, String remarks, String isstop, String userid, String phone, String username, String headaddress) {
        this.searchid = searchid;
        this.searchtitle = searchtitle;
        this.searchsubmittime = searchsubmittime;
        this.searchtype = searchtype;
        this.searchpersonid = searchpersonid;
        this.questionone = questionone;
        this.questiontwo = questiontwo;
        this.questionthree = questionthree;
        this.remarks = remarks;
        this.isstop = isstop;
        this.userid = userid;
        this.phone = phone;
        this.username = username;
        this.headaddress = headaddress;
    }

    public String getSearchid() {
        return searchid;
    }

    public void setSearchid(String searchid) {
        this.searchid = searchid;
    }

    public String getSearchtitle() {
        return searchtitle;
    }

    public void setSearchtitle(String searchtitle) {
        this.searchtitle = searchtitle;
    }

    public String getSearchsubmittime() {
        return searchsubmittime;
    }

    public void setSearchsubmittime(String searchsubmittime) {
        this.searchsubmittime = searchsubmittime;
    }

    public String getSearchtype() {
        return searchtype;
    }

    public void setSearchtype(String searchtype) {
        this.searchtype = searchtype;
    }

    public String getSearchpersonid() {
        return searchpersonid;
    }

    public void setSearchpersonid(String searchpersonid) {
        this.searchpersonid = searchpersonid;
    }

    public String getQuestionone() {
        return questionone;
    }

    public void setQuestionone(String questionone) {
        this.questionone = questionone;
    }

    public String getQuestiontwo() {
        return questiontwo;
    }

    public void setQuestiontwo(String questiontwo) {
        this.questiontwo = questiontwo;
    }

    public String getQuestionthree() {
        return questionthree;
    }

    public void setQuestionthree(String questionthree) {
        this.questionthree = questionthree;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getIsstop() {
        return isstop;
    }

    public void setIsstop(String isstop) {
        this.isstop = isstop;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public String getHeadaddress() {
        return headaddress;
    }

    public void setHeadaddress(String headaddress) {
        this.headaddress = headaddress;
    }

    @Override
    public String toString() {
        return "SearchAndPerson{" +
                "searchid=" + searchid +
                ", searchtitle='" + searchtitle + '\'' +
                ", searchsubmittime='" + searchsubmittime + '\'' +
                ", searchtype='" + searchtype + '\'' +
                ", searchpersonid=" + searchpersonid +
                ", questionone='" + questionone + '\'' +
                ", questiontwo='" + questiontwo + '\'' +
                ", questionthree='" + questionthree + '\'' +
                ", remarks='" + remarks + '\'' +
                ", isstop=" + isstop +
                ", userid=" + userid +
                ", phone='" + phone + '\'' +
                ", username='" + username + '\'' +
                ", headaddress='" + headaddress + '\'' +
                '}';
    }
}
