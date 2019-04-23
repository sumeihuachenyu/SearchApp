package com.example.lenovo.searchapp.person.model;

/**
 * Created by lenovo on 2019-04-01.
 */
public class User {
    private Integer userid;

    private String phone;

    private String username;

    private String password;

    private String token;

    private String headaddress;

    public User(Integer userid, String phone, String username, String password, String token, String headaddress) {
        this.userid = userid;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.token = token;
        this.headaddress = headaddress;
    }

    public User() {
        super();
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public String getHeadaddress() {
        return headaddress;
    }

    public void setHeadaddress(String headaddress) {
        this.headaddress = headaddress == null ? null : headaddress.trim();
    }

    @Override
    public String toString() {
        return "User{" +
                "userid=" + userid +
                ", phone='" + phone + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", headaddress='" + headaddress + '\'' +
                '}';
    }
}
