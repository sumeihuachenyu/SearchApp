package com.example.lenovo.searchapp.entity;

/**
 * Created by lenovo on 2019-03-16.
 */
public class Person {
    private String headImg;
    private String phone;
    private String username;
    private String password;

    public Person(String headImg, String phone, String username, String password) {
        this.headImg = headImg;
        this.phone = phone;
        this.username = username;
        this.password = password;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Person{" +
                "headImg='" + headImg + '\'' +
                ", phone='" + phone + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
