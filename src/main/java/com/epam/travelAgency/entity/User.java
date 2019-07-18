package com.epam.travelAgency.entity;

import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Component
public class User extends Entity {

    private long userId;
    private String login;
    private String password;

    public User() {
        this.userId = UUID.randomUUID().timestamp();//TODO unique ID because generated ID can be the same with DB ID
    }

    public User(long userId, String login, String password) {
        this.userId = userId;
        this.login = login;
        this.password = password;
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

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        } else {
            User user = (User) o;
            return getUserId() == user.getUserId() &&
                    getLogin().equals(user.getLogin()) &&
                    getPassword().equals(user.getPassword());
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getLogin(), getPassword());
    }

    @Override
    public String toString() {
        return new StringBuilder(getClass().getSimpleName()).append("userId=")
                .append(userId).append(", login='").append(login).append('\'')
                .append(", password='").append(password).append('\'').append('}').toString();
    }

}
