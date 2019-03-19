package com.example.lenovo.searchapp.entity;

import com.example.lenovo.searchapp.common.BaseResult;

/**
 * Created by lenovo on 2019-03-07.
 */
public class LoginResult extends BaseResult {
    private UserInfo result;

    public UserInfo getResult() {
        return result;
    }

    public void setResult(UserInfo result) {
        this.result = result;
    }

    public class UserInfo {

        private String token;
        private String userId;
        private String userName;
        private String portrait;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPortrait() {
            return portrait;
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
        }

        @Override
        public String toString() {
            return "UserInfo{" +
                    "token='" + token + '\'' +
                    ", userId='" + userId + '\'' +
                    ", userName='" + userName + '\'' +
                    ", portrait='" + portrait + '\'' +
                    '}';
        }
    }
}
