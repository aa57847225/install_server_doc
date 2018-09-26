package com.whl.demo.entity;

/**
 * @program: cache-redis
 * @description: 用户信息
 * @author: Mr.Wang
 * @create: 2018-09-21 09:10
 **/
public class User {

    private String userId;
    private String userName;
    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
