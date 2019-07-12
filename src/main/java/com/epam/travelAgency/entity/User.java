package com.epam.travelAgency.entity;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class User {

    private long userId;
    private String login;
    private String password;

    public User() {
        this.userId = UUID.randomUUID().timestamp();//TODO unique ID because generated ID can be the same with DB ID
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
