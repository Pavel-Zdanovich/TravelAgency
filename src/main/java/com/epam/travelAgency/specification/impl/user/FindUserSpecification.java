package com.epam.travelAgency.specification.impl.user;

import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.entity.metamodel.User_;
import com.epam.travelAgency.specification.FindSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Component
public class FindUserSpecification implements FindSpecification<User, User> {

    public static final String SELECT_USER = "SELECT * FROM users WHERE user_id = %s AND login = '%s' AND password = '%s'";
    @Autowired
    private User user;

    public FindUserSpecification() {
    }

    public FindUserSpecification(User user) {
        this.user = user;
    }

    @Override
    public void setSpecification(User user) {
        this.user = user;
    }

    @Override
    public User getSpecification() {
        return this.user;
    }

    @Override
    public String getSQLQuery() {
        return String.format(SELECT_USER, user.getUserId(), user.getLogin(), user.getPassword());
    }

    @Override
    public CriteriaQuery<User> getCriteriaQuery(CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        return criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(User_.USER_ID), user.getUserId()),
                criteriaBuilder.equal(root.get(User_.LOGIN), user.getLogin()),
                criteriaBuilder.equal(root.get(User_.PASSWORD), user.getPassword()));
    }

}
