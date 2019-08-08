package com.epam.travelAgency.specification.impl.user;

import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.entity.metamodel.User_;
import com.epam.travelAgency.specification.FindSpecification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Component
public class FindUserByLoginSpecification implements FindSpecification<User, String> {

    public static final String SELECT_USER_BY_LOGIN = "SELECT * FROM users WHERE login = '%s'";
    private String login;

    public FindUserByLoginSpecification() {
    }

    public FindUserByLoginSpecification(String login) {
        this.login = login;
    }

    @Override
    public void setSpecification(String login) {
        this.login = login;
    }

    @Override
    public String getSpecification() {
        return this.login;
    }

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_USER_BY_LOGIN, this.login);
    }

    @Override
    public CriteriaQuery<User> getCriteriaQuery(CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        return criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(User_.LOGIN), this.login));
    }

}
