package com.example.lenovo.searchapp.home.model;

/**
 * Created by lenovo on 2019-04-14.
 * 包含参与记录、调查、用户数据，主要在参与模块使用
 */
public class JoinSearchPerson extends SearchAndPerson {
    private String joinid;

    private String joinpersonid;

    private String jointime;

    private String answerone;

    private String answertwo;

    private String answerthree;

    private String otheranswer;

    private String joinsearchid;

    public String getJoinid() {
        return joinid;
    }

    public void setJoinid(String joinid) {
        this.joinid = joinid;
    }

    public String getJoinpersonid() {
        return joinpersonid;
    }

    public void setJoinpersonid(String joinpersonid) {
        this.joinpersonid = joinpersonid;
    }

    public String getJointime() {
        return jointime;
    }

    public void setJointime(String jointime) {
        this.jointime = jointime;
    }

    public String getAnswerone() {
        return answerone;
    }

    public void setAnswerone(String answerone) {
        this.answerone = answerone;
    }

    public String getAnswertwo() {
        return answertwo;
    }

    public void setAnswertwo(String answertwo) {
        this.answertwo = answertwo;
    }

    public String getAnswerthree() {
        return answerthree;
    }

    public void setAnswerthree(String answerthree) {
        this.answerthree = answerthree;
    }

    public String getOtheranswer() {
        return otheranswer;
    }

    public void setOtheranswer(String otheranswer) {
        this.otheranswer = otheranswer;
    }

    public String getJoinsearchid() {
        return joinsearchid;
    }

    public void setJoinsearchid(String joinsearchid) {
        this.joinsearchid = joinsearchid;
    }

    public JoinSearchPerson(String joinid, String joinpersonid, String jointime, String answerone, String answertwo, String answerthree, String otheranswer, String joinsearchid) {
        this.joinid = joinid;
        this.joinpersonid = joinpersonid;
        this.jointime = jointime;
        this.answerone = answerone;
        this.answertwo = answertwo;
        this.answerthree = answerthree;
        this.otheranswer = otheranswer;
        this.joinsearchid = joinsearchid;
    }
    public JoinSearchPerson(){}
}
