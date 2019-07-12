package com.epam.travelAgency.specification.impl;

import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.repository.SqlOperator;
import com.epam.travelAgency.specification.Specification;

public class LoginSpecification implements Specification<User> {

    public enum Type {
        EQUALS,
        CONTAINS,
        ENDS_WITH,
        EQUALS_IGNORE_CASE,
        MATCHES,
        STARTS_WITH
    }

    public static final String SELECT_USER_BY_LOGIN = "SELECT * FROM users WHERE login = %s";
    public static final String SELECT_USER_BY_REGEX = "SELECT regex_matches(login, %s) FROM users";
    public static final String INSERT_USER_BY_LOGIN = "";
    private Type type;
    private String login;

    public LoginSpecification(Type type, String login) {
        this.type = type;
        this.login = login;
    }

    @Override
    public boolean specified(User parameter) {
        boolean result;
        switch (this.type) {
            case EQUALS: {
                result = this.login.equals(parameter.getLogin());
                break;
            }
            case CONTAINS: {
                result = this.login.contains(parameter.getLogin());
                break;
            }
            case ENDS_WITH: {
                result = this.login.endsWith(parameter.getLogin());
                break;
            }
            case EQUALS_IGNORE_CASE: {
                result = this.login.equalsIgnoreCase(parameter.getLogin());
                break;
            }
            case STARTS_WITH: {
                result = this.login.startsWith(parameter.getLogin());
                break;
            }
            case MATCHES: {
                result = this.login.matches(parameter.getLogin());
                break;
            }
            default: {
                throw new IllegalArgumentException();
            }
        }
        return result;
    }

    @Override
    public String getSqlQuery(SqlOperator sqlOperator) {
        String result;
        switch (this.type) {
            case EQUALS: {
                result = String.format(SELECT_USER_BY_LOGIN, login);
                break;
            }
            case ENDS_WITH: {
                result = String.format(SELECT_USER_BY_REGEX, ".+" + login + "$");
                break;
            }
            case EQUALS_IGNORE_CASE: {
                result = String.format(SELECT_USER_BY_REGEX, login + ", \"i\"");
                break;
            }
            case STARTS_WITH: {
                result = String.format(SELECT_USER_BY_REGEX, "^" + login + ".+");
                break;
            }
            default: {
                result = String.format(SELECT_USER_BY_REGEX, login);
            }
        }
        return result;
    }

}
