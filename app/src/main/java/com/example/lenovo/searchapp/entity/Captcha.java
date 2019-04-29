package com.example.lenovo.searchapp.entity;

/**
 * Created by lenovo on 2019-04-02.
 * 验证码实体
 */
public class Captcha {
    private Integer id;

    private String captcha;

    private String phone;

    private String endtime;

    public Captcha(Integer id, String captcha, String phone, String endtime) {
        this.id = id;
        this.captcha = captcha;
        this.phone = phone;
        this.endtime = endtime;
    }

    public Captcha(String captcha, String phone, String endtime) {
        this.captcha = captcha;
        this.phone = phone;
        this.endtime = endtime;
    }

    public Captcha() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha == null ? null : captcha.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEndtime() {
        return endtime;
    }

    @Override
    public String toString() {
        return "Captcha{" +
                "id=" + id +
                ", captcha='" + captcha + '\'' +
                ", phone='" + phone + '\'' +
                ", endtime='" + endtime + '\'' +
                '}';
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime == null ? null : endtime.trim();
    }

}
